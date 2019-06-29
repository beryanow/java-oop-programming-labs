package ru.nsu.g.beryanov.phrases_count;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import ru.nsu.g.beryanov.phrases_count.InputConditions;

public class InputConditionsTest {
	@Test
	public void testArgsInput() {
		String[] args1 = {"-n", "3", "-m", "4", "-"};

		InputConditions NewIC = new InputConditions();
		NewIC.resolve_conditions(args1);

		int final_n = NewIC.get_n();
		int final_m = NewIC.get_m();

		assertEquals(final_n, 3);
		assertEquals(final_m, 4);
	}
	@Test
	public void testArgsStringInput() {
		String[] args1 = {"-n", "3", "-m", "4", "example.txt"};

		InputConditions NewIC = new InputConditions();
		NewIC.resolve_conditions(args1);

		String final_s = NewIC.get_s();
		assertEquals(final_s, "example.txt");
	}
}
