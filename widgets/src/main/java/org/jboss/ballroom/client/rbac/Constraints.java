package org.jboss.ballroom.client.rbac;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Heiko Braun
 * @date 7/9/13
 */
public class Constraints {


    private boolean readConfig,writeConfig,readRuntime,writeRuntime;

    private Map<String, AttributePerm> attributePermissions = new HashMap<String,AttributePerm>();

    private boolean address = true;

    public boolean isAddress() {
        return address;
    }

    public void setAddress(boolean access) {
        this.address = access;
    }

    public void setReadConfig(boolean readConfig) {
        this.readConfig = readConfig;
    }

    public void setWriteConfig(boolean writeConfig) {
        this.writeConfig = writeConfig;
    }

    public void setReadRuntime(boolean readRuntime) {
        this.readRuntime = readRuntime;
    }

    public void setWriteRuntime(boolean writeRuntime) {
        this.writeRuntime = writeRuntime;
    }

    public boolean isReadConfig() {
        return readConfig;
    }

    public boolean isWriteConfig() {
        return writeConfig;
    }

    public boolean isReadRuntime() {
        return readRuntime;
    }

    public boolean isWriteRuntime() {
        return writeRuntime;
    }

    public void setAttributeRead(String name, boolean canBeRead)
    {
        this.attributePermissions.put(name, new AttributePerm(canBeRead));
    }

    public boolean isAttributeRead(String name) {
        return attributePermissions.containsKey(name) ?
                attributePermissions.get(name).isRead() : true;
    }

    public void setAttributeWrite(String name, boolean b)
    {
        if(!attributePermissions.containsKey(name)) {
            attributePermissions.put(name, new AttributePerm(true));
        }

        attributePermissions.get(name).setWrite(b);

    }

    public boolean isAttributeWrite(String name) {
        return attributePermissions.containsKey(name) ?
                attributePermissions.get(name).isWrite() : true;
    }

    class AttributePerm
    {

        AttributePerm(boolean read) {
            this.read = read;
        }

        boolean read,write;

        boolean isRead() {
            return read;
        }

        boolean isWrite() {
            return write;
        }

        void setWrite(boolean write) {
            this.write = write;
        }
    }
}
