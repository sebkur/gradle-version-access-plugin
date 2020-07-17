package com.example;

import org.junit.Assert;
import org.junit.Test;

import com.example.test.Version;

public class UnitTest
{

	@Test
	public void test()
	{
		Assert.assertEquals("0.0.1", Version.getVersion());
	}

}
