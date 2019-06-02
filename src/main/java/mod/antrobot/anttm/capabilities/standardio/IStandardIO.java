package mod.antrobot.anttm.capabilities.standardio;

import mod.antrobot.anttm.capabilities.standardio.EnumIO.*;

import java.util.HashMap;

public interface IStandardIO {
    void initMap(EnumType up,EnumType right,EnumType down,EnumType left);

    HashMap<EnumDir,EnumType> getSides();
    HashMap<EnumDir,EnumType> getSidesOf(EnumType type);

    HashMap<EnumDir,Integer> getSlots(int slot);
    HashMap<EnumDir,Integer> getSlotsOf(int slot,EnumType type);

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

    int calcFace(int slot,EnumDir dir);

    boolean isValidSlot(int slot);

    EnumDir find(EnumDir dir,EnumType type,boolean next);

    void cycle(EnumType type);
    void rotate();

    void rotateCurrent(EnumType type);

    EnumDir getCurrent(EnumType type);
    void setCurrent(EnumType type,EnumDir dir);
    EnumDir getNext(EnumType type);
}
