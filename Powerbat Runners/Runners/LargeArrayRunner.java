import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Random;

import org.powerbat.executor.Result;
import org.powerbat.interfaces.Manifest;
import org.powerbat.interfaces.ProjectSet;

@Manifest(category = "Logic", instructions = "Return the array that was a larger sum of the 2.", version = 0.0d)
public class LargeArrayRunner implements ProjectSet {

	Manifest prop = getClass().getAnnotation(Manifest.class);

	@Override
	public Result[] getResults(Class<?> clazz) {
		try {
			final Method method = clazz.getMethod("largerArray", int[].class,
					int[].class);
			Result[] results = new Result[10];
			final Random random = new Random();
			for (int i = 0; i < results.length; i++) {
				final int count = random.nextInt(5);
				int[] a = new int[count];
				for (int j = 0; j < a.length; j++) {
					a[j] = random.nextInt(50);
				}
				int[] b = new int[count];
				for (int j = 0; j < b.length; j++) {
					b[j] = random.nextInt(50);
				}
				results[i] = new Result(Arrays.toString((int[]) method.invoke(
						clazz.newInstance(), a, b)), Arrays.toString(larger(a,
						b)), Arrays.toString(a), Arrays.toString(b));
			}
			return results;
		} catch (Exception e) {
			return null;
		}
	}

	private int[] larger(int[] a, int[] b) {
		int sum = 0;
		for (int i = 0; i < Math.max(a.length, b.length); i++) {
			sum += a[i] - b[i];
		}
		return sum > 0 ? a : b;
	}

	@Override
	public double getVersion() {
		return prop.version();
	}

	@Override
	public String getInstructions() {
		return prop.instructions();
	}

	@Override
	public String getCategory() {
		return prop.category();
	}

	@Override
	public String getSkeleton() {
		return "public class LargeArray {\n\n\tpublic int[] largerArray(int[] a, int[] b){\n\t\n\t}\n}";
	}

}
