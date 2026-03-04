package com.austinv11.peripheralsplusplus.utils;

import com.austinv11.peripheralsplusplus.reference.Reference;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraftforge.server.ServerLifecycleHooks;

import javax.annotation.Nullable;
import java.util.*;

public class Util {

public static HashMap<Integer, Object> iteratorToMap(Iterator iterator) {
HashMap<Integer,Object> map = new HashMap<>();
int i = 1;
while (iterator.hasNext()) {
map.put(i+1, iterator.next());
i++;
}
return map;
}

public static HashMap<Integer, Object> collectionToMap(Collection iterable) {
HashMap<Integer,Object> map = new HashMap<>();
Iterator<Object> types = iterable.iterator();
for (int i = 0; i < iterable.size(); i++) {
map.put(i+1, types.next());
}
return map;
}

public static HashMap<Integer,Integer> arrayToMap(int[] array) {
HashMap<Integer,Integer> map = new HashMap<>();
for (int i = 0; i < array.length; i++) {
map.put(i+1, array[i]);
}
return map;
}

public static HashMap<Integer,Byte> arrayToMap(byte[] array) {
HashMap<Integer,Byte> map = new HashMap<>();
for (int i = 0; i < array.length; i++) {
map.put(i+1, array[i]);
}
return map;
}

public static HashMap<Integer,Object> arrayToMap(Object[] array) {
HashMap<Integer,Object> map = new HashMap<>();
for (int i = 0; i < array.length; i++) {
map.put(i+1, array[i]);
}
return map;
}

public static HashMap<Integer, String> getOreDictEntries(ItemStack stack) {
// OreDict removed in 1.20.1 - tags are used instead
return new HashMap<>();
}

public static boolean compareItemStacksViaOreDict(ItemStack stack1, ItemStack stack2) {
return ItemStack.isSameItemSameTags(stack1, stack2);
}

public static CompoundTag writeToBookNBT(String title, String author, List<String> pageText) {
CompoundTag tag = new CompoundTag();
tag.putString("author", author);
String displayTitle = title;
if (displayTitle.length() > 32)
displayTitle = displayTitle.substring(0, 29) + "...";
tag.putString("title", displayTitle);
ListTag list = new ListTag();
for (String s : pageText) {
JsonObject page = new JsonObject();
page.addProperty("text", s);
list.add(StringTag.valueOf(page.toString()));
}
tag.put("pages", list);
return tag;
}

public static CompoundTag writeToBookNBT(String title, List<String> pageText) {
return writeToBookNBT(title, Reference.MOD_NAME, pageText);
}

public static String listToString(List<String> list) {
String returnVal = "";
for (String s : list)
returnVal = returnVal + s + "\n";
return returnVal;
}

public static Object keyFromVal(HashMap map, Object val) {
for (Object key : map.keySet())
if (map.get(key).equals(val))
return key;
return null;
}

public static Player getPlayer(String ign) {
List<Player> players = new ArrayList<>();
for (ServerLevel serverLevel : ServerLifecycleHooks.getCurrentServer().getAllLevels())
players.addAll(serverLevel.players());
for (Player p : players) {
if (p.getName().getString().equalsIgnoreCase(ign))
return p;
}
return null;
}

public static String[] stringToArray(String array) {
String[] array_ = array.replace("]", "").replace("[", "").split(",");
for (int i = 0; i < array_.length; i++)
array_[i] = array_[i].trim();
return array_;
}

public static List<String> getPlayers(Level level) {
List<String> list = new ArrayList<>();
if (level != null)
for (Player player : level.players())
list.add(player.getName().getString());
else
for (ServerLevel serverLevel : ServerLifecycleHooks.getCurrentServer().getAllLevels())
for (Player player : serverLevel.players())
list.add(player.getName().getString());
return list;
}

public static Entity getEntityFromId(UUID entityId) {
for (ServerLevel serverLevel : ServerLifecycleHooks.getCurrentServer().getAllLevels()) {
for (Entity entity : serverLevel.getAllEntities()) {
if (entity.getUUID().equals(entityId))
return entity;
}
}
return null;
}

/**
 * Convert a byte array to an unsigned int array of the same size
 */
public static int[] byteArraytoUnsignedIntArray(byte[] bytes) {
int[] intArray = new int[bytes.length];
for (int byteIndex = 0; byteIndex < bytes.length; byteIndex++) {
intArray[byteIndex] = Byte.toUnsignedInt(bytes[byteIndex]);
}
return intArray;
}

/**
 * Get a player based on their persistent id
 */
@Nullable
public static Player getPlayer(UUID persistentID) {
List<Player> players = new ArrayList<>();
for (ServerLevel serverLevel : ServerLifecycleHooks.getCurrentServer().getAllLevels())
players.addAll(serverLevel.players());
for (Player p : players) {
if (p.getUUID().equals(persistentID))
return p;
}
return null;
}
}
