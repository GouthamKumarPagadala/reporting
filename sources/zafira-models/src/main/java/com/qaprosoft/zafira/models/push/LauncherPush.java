package com.qaprosoft.zafira.models.push;

import com.qaprosoft.zafira.models.db.Launcher;

import java.util.List;

import static com.qaprosoft.zafira.models.push.AbstractPush.Type.LAUNCHER;

public class LauncherPush extends AbstractPush {

    private final List<Launcher> launchers;
    private final boolean success;

    public LauncherPush(List<Launcher> launchers, boolean success) {
        super(LAUNCHER);
        this.launchers = launchers;
        this.success = success;
    }

    public List<Launcher> getLaunchers() {
        return launchers;
    }

    public boolean isSuccess() {
        return success;
    }

}
