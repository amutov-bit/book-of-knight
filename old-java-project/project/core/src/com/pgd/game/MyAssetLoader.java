package com.pgd.game;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;


public class MyAssetLoader extends AsynchronousAssetLoader<File, MyAssetLoader.FileParameter> {

    public MyAssetLoader(FileHandleResolver resolver) {

        super(resolver);

    }

    File text;

    @Override
    public void loadAsync(AssetManager manager, String fileName, FileHandle file, FileParameter parameter) {

        this.text = null;
        this.text = new File(file);

    }

    @Override
    public File loadSync(AssetManager manager, String fileName, FileHandle file, FileParameter parameter) {

        File text = this.text;
        this.text = null;

        return text;

    }

    @SuppressWarnings("rawtypes")
    @Override
    public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, FileParameter parameter) {

        return null;

    }

    public static class FileParameter extends AssetLoaderParameters<File> {

    }

}