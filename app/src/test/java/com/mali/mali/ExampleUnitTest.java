package com.mali.mali;

import org.junit.Test;

import buaa.jj.designpattern.factory.FileSystemFactory;
import shisong.FactoryBuilder;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        FactoryBuilder.getInstance(true).initFileSystem();
    }
}