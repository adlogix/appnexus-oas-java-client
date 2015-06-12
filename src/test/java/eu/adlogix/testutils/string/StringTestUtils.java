package eu.adlogix.testutils.string;

public class StringTestUtils {

	private static final String LINE_SEPARATOR = System.getProperty("line.separator");
	private static final String PLATFORM = System.getProperty("os.name").toLowerCase();

	public static final String normalizeNewLinesToCurPlatform(final String stringToNormalize) {
		return (PLATFORM.indexOf("windows") >= 0) ? stringToNormalize : stringToNormalize.replace("\n", LINE_SEPARATOR);
	}
}
