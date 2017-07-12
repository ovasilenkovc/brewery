package com.brewery.content;

public abstract class File implements Content {

    private byte[] base64encode;

    private String base64encodeString;

    public abstract Long getId();

    public abstract void setId(Long id);

    public abstract String getName();

    public abstract void setName(String name);

    public abstract String getPath();

    public abstract void setPath(String path);

    public byte[] getBase64encode() {
        return base64encode;
    }

    public void setBase64encode(byte[] base64encode) {
        this.base64encode = base64encode;
    }

    public String getBase64encodeString() {
        return base64encodeString;
    }

    public void setBase64encodeString(String base64encodeString) {
        this.base64encodeString = base64encodeString;
    }
}
