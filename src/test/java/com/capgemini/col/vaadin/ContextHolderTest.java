package com.capgemini.col.vaadin;

import static org.junit.Assert.assertSame;

import org.junit.Before;
import org.junit.Test;

public class ContextHolderTest {

	private ContextHolder holder = new ContextHolder();

	@Before
	public void setupFixture() {
		holder = new ContextHolder();
	}

	@Test
	public void retrievalWorks() {
		Integer context = Integer.valueOf(1);
		holder.add(Integer.class, context);
		assertSame(context, holder.getContext(Integer.class));
	}

	@Test
	public void subClassesAreRegisteredUnderSuperClass() {
		Integer context = Integer.valueOf(1);
		holder.add(Number.class, context);
		assertSame(context, holder.getContext(Number.class));
	}
}
