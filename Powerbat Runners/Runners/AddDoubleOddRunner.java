import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Random;

import org.powerbat.executor.Result;
import org.powerbat.interfaces.Manifest;
import org.powerbat.interfaces.ProjectSet;

@Manifest(category = "Logic", instructions = "Add parameters a and b, but if a is odd, return double the sum. Use a \"addOdd\" method to perform this.", version = 1.0)
public class AddDoubleOddRunner implements ProjectSet {

	private static final Manifest properties = AddDoubleOddRunner.class
			.getAnnotation(Manifest.class);

	Random random = new Random();

	@Override
	public Result[] getResults(Class<?> c) {
		try {
			final Object instance = c.newInstance();
			final Method m = c.getMethod("addOdd", int.class, int.class);
			final int count = 10;
			final ArrayList<Result> results = new ArrayList<Result>();
			for (int i = 0; i < count; i++) {
				final int x = -3000 + random.nextInt(6000);
				final int y = -3000 + random.nextInt(6000);
				final int correct = (x + y) * (x % 2 == 0 ? 1 : 2);
				final int answer = (Integer) m.invoke(instance, x, y);

				results.add(new Result(answer, correct, x, y));
			}
			return results.toArray(new Result[results.size()]);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Result[] {};
	}

	@Override
	public double getVersion() {
		return properties.version();
	}

	@Override
	public String getInstructions() {
		return properties.instructions();
	}

	@Override
	public String getSkeleton() {
		return  "public class AddDoubleOdd {\n\n    public int addOdd(int a, int b){\n        \n    }\n}";
	}

	@Override
	public String getCategory() {
		return null;
	}

}
