package mod.antrobot.anttm.capabilities.standardio;

import mod.antrobot.anttm.capabilities.standardio.EnumIO.*;
import net.minecraft.entity.player.InventoryPlayer;

import java.util.HashMap;

public interface IStandardIO {
    void initMap(EnumType up,EnumType right,EnumType down,EnumType left);

    HashMap<EnumDir,EnumType> getSides();
    HashMap<EnumDir,EnumType> getSidesOf(EnumType type);

    HashMap<EnumDir,Integer> getSlots(InventoryPlayer inv,int slot);
    HashMap<EnumDir,Integer> getSlotsOf(InventoryPlayer inv,int slot,EnumType type);

    int getAmount(EnumType type);

    void setDir(EnumDir dir, EnumType type);
    void setDirForce(EnumDir dir, EnumType type);

    EnumType getFace(EnumDir dir);
    boolean isType(EnumDir dir, EnumType type);

    boolean isValidDir(EnumDir dir);

    int calcLeft(int slot);
    int calcRight(int slot);
    int calcUp(int slot);
    int calcDown(int slot);

    int calcFaceExtend(InventoryPlayer inv,int slot,EnumDir dir);

    boolean isValidSlot(InventoryPlayer inv,int slot);

    EnumDir find(EnumDir dir,EnumType type,boolean next);

    void cycle(EnumType type);
    void rotate();

    void rotateCurrent(EnumType type);

    EnumDir getCurrent(EnumType type);
    void setCurrent(EnumType type,EnumDir dir);
    EnumDir getNext(EnumType type);
}
