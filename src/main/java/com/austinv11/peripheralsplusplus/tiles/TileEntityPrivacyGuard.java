package com.austinv11.peripheralsplusplus.tiles;

import com.austinv11.peripheralsplusplus.utils.IPlusPlusPeripheral;
import dan200.computercraft.api.lua.IArguments;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import name.neuhalfen.projects.crypto.bouncycastle.openpgp.BouncyGPG;
import name.neuhalfen.projects.crypto.bouncycastle.openpgp.keys.callbacks.KeyringConfigCallbacks;
import name.neuhalfen.projects.crypto.bouncycastle.openpgp.keys.keyrings.InMemoryKeyring;
import name.neuhalfen.projects.crypto.bouncycastle.openpgp.keys.keyrings.KeyringConfigs;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openpgp.*;
import org.bouncycastle.openpgp.operator.PGPDigestCalculator;
import org.bouncycastle.openpgp.operator.bc.BcKeyFingerprintCalculator;
import org.bouncycastle.openpgp.operator.bc.BcPGPDigestCalculatorProvider;
import org.bouncycastle.openpgp.operator.bc.BcPGPKeyPair;
import org.bouncycastle.openpgp.PGPUtil;
import org.bouncycastle.bcpg.HashAlgorithmTags;
import org.bouncycastle.crypto.generators.RSAKeyPairGenerator;
import org.bouncycastle.crypto.params.RSAKeyGenerationParameters;
import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.util.io.Streams;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.*;
import java.math.BigInteger;
import java.security.*;
import java.util.Date;
import java.util.HashMap;

public class TileEntityPrivacyGuard extends BlockEntity implements IPlusPlusPeripheral.HasPeripheral {

private static final String ENCODING = "US-ASCII";

public TileEntityPrivacyGuard(BlockPos pos, BlockState state) {
super(com.austinv11.peripheralsplusplus.init.ModTileEntities.PRIVACY_GUARD.get(), pos, state);
if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null)
Security.addProvider(new BouncyCastleProvider());
}
public final Object[] generateKey(IArguments args) throws LuaException {
String id = args.getString(0);
int size = args.count() > 1 ? args.getInt(1) : 2048;
String password = args.count() > 2 ? args.getString(2) : null;
try {
RSAKeyPairGenerator generator = new RSAKeyPairGenerator();
generator.init(new RSAKeyGenerationParameters(BigInteger.valueOf(0x10001), new SecureRandom(), size, 12));
BcPGPKeyPair rsaKeyPair = new BcPGPKeyPair(PGPPublicKey.RSA_GENERAL, generator.generateKeyPair(), new Date());

PGPSignatureSubpacketGenerator hashedSubpacketVector = new PGPSignatureSubpacketGenerator();
PGPSignatureSubpacketGenerator unhashedSubpacketVector = new PGPSignatureSubpacketGenerator();
PGPDigestCalculator sha1Calc = new BcPGPDigestCalculatorProvider().get(HashAlgorithmTags.SHA1);
org.bouncycastle.openpgp.operator.PBESecretKeyEncryptor encryptor = password != null ?
new org.bouncycastle.openpgp.operator.bc.BcPBESecretKeyEncryptorBuilder(PGPEncryptedData.AES_256, sha1Calc).build(password.toCharArray()) : null;

PGPKeyRingGenerator keyRingGenerator = new PGPKeyRingGenerator(
PGPSignature.POSITIVE_CERTIFICATION, rsaKeyPair, id, sha1Calc,
hashedSubpacketVector.generate(), unhashedSubpacketVector.generate(),
new org.bouncycastle.openpgp.operator.bc.BcPGPContentSignerBuilder(rsaKeyPair.getPublicKey().getAlgorithm(), HashAlgorithmTags.SHA1),
encryptor);

ByteArrayOutputStream pubOut = new ByteArrayOutputStream();
ByteArrayOutputStream secOut = new ByteArrayOutputStream();
try (OutputStream pub = new org.bouncycastle.bcpg.ArmoredOutputStream(pubOut);
 OutputStream sec = new org.bouncycastle.bcpg.ArmoredOutputStream(secOut)) {
keyRingGenerator.generatePublicKeyRing().encode(pub);
keyRingGenerator.generateSecretKeyRing().encode(sec);
}
HashMap<String, String> result = new HashMap<>();
result.put("public", pubOut.toString(ENCODING));
result.put("private", secOut.toString(ENCODING));
return new Object[]{result};
} catch (Exception e) {
throw new LuaException("Key generation failed: " + e.getMessage());
}
}

public final Object[] readKey(IArguments args) throws LuaException {
String keyStr = args.getString(0);
try {
PGPPublicKeyRing ring = new PGPPublicKeyRing(
PGPUtil.getDecoderStream(new ByteArrayInputStream(keyStr.getBytes(ENCODING))),
new BcKeyFingerprintCalculator());
PGPPublicKey key = ring.getPublicKey();
HashMap<String, Object> info = new HashMap<>();
info.put("keyId", Long.toHexString(key.getKeyID()));
info.put("fingerprint", Base64.toBase64String(key.getFingerprint()));
return new Object[]{info};
} catch (Exception e) {
throw new LuaException("Key read failed: " + e.getMessage());
}
}

public final Object[] encrypt(IArguments args) throws LuaException {
String keyStr = args.getString(0);
String plaintext = args.getString(1);
try {
InMemoryKeyring keyring = KeyringConfigs.forGpgExportedKeys(KeyringConfigCallbacks.withUnprotectedKeys());
keyring.addPublicKey(keyStr.getBytes(ENCODING));
ByteArrayOutputStream out = new ByteArrayOutputStream();
try (OutputStream encStream = BouncyGPG.encryptToStream()
.withConfig(keyring)
.withStrongAlgorithms()
.toRecipients(new String[0])
.andDoNotSign()
.binaryOutput()
.andWriteTo(out)) {
encStream.write(plaintext.getBytes(ENCODING));
}
return new Object[]{Base64.toBase64String(out.toByteArray())};
} catch (Exception e) {
throw new LuaException("Encryption failed: " + e.getMessage());
}
}

public final Object[] decrypt(IArguments args) throws LuaException {
String keyStr = args.getString(0);
String password = args.getString(1);
String ciphertext = args.getString(2);
try {
InMemoryKeyring keyring = KeyringConfigs.forGpgExportedKeys(
KeyringConfigCallbacks.withPassword(password));
keyring.addSecretKey(keyStr.getBytes(ENCODING));
byte[] cipherBytes = Base64.decode(ciphertext);
try (InputStream decStream = BouncyGPG.decryptAndVerifyStream()
.withConfig(keyring)
.andIgnoreSignatures()
.fromEncryptedInputStream(new ByteArrayInputStream(cipherBytes))) {
ByteArrayOutputStream plainOut = new ByteArrayOutputStream();
Streams.pipeAll(decStream, plainOut);
return new Object[]{plainOut.toString(ENCODING)};
}
} catch (Exception e) {
throw new LuaException("Decryption failed: " + e.getMessage());
}
}
    private final IPeripheral peripheral = new IPeripheral() {
        @Override
        public String getType() { return "privacy_guard"; }

        @Override
        public boolean equals(IPeripheral other) { return TileEntityPrivacyGuard.this == other; }

        @LuaFunction
        public final Object[] generateKey(IArguments args) throws LuaException {
            return TileEntityPrivacyGuard.this.generateKey(args);
        }

        @LuaFunction
        public final Object[] readKey(IArguments args) throws LuaException {
            return TileEntityPrivacyGuard.this.readKey(args);
        }

        @LuaFunction
        public final Object[] encrypt(IArguments args) throws LuaException {
            return TileEntityPrivacyGuard.this.encrypt(args);
        }

        @LuaFunction
        public final Object[] decrypt(IArguments args) throws LuaException {
            return TileEntityPrivacyGuard.this.decrypt(args);
        }

    };

    @Override
    public IPeripheral getModPeripheral() { return peripheral; }

}
