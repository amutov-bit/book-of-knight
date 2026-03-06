package com.pgd.game;

import com.badlogic.gdx.files.FileHandle;

public class File {

    private FileHandle file;

    public File() {


    }

    public File(byte[] data) {


    }

    public File(String string) {


    }

    public File(FileHandle file) {

    	this.file = file;
    }
    
    public FileHandle getFile(){
    	return file;
    }
}