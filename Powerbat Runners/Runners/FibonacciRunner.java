import java.lang.reflect.Method;
import java.util.Random;

import org.powerbat.executor.Result;
import org.powerbat.interfaces.Manifest;
import org.powerbat.interfaces.ProjectSet;

@Manifest(category = "recursive", instructions = "Return the nth number fibonacci number", version = 1.0)
public class FibonacciRunner implements ProjectSet {

	Manifest manifest = FibonacciRunner.class.getAnnotation(Manifest.class);

	@Override
	public Result[] getResults(Class<?> classToTest) {
		try {
			Method method = classToTest.getMethod("fibonacci", int.class);
			Random ran = new Random();
			Result[] results = new Result[10];
			for (int i = 0; i < 10; i++) {
				int num = ran.nextInt(30);
				results[i] = new Result(method.invoke(
						classToTest.newInstance(), num), fibonacci(num), num);
			}
			return results;
		} catch (Exception e) {
			return new Result[] { new Result("Error", "executing"),
					new Result("possibly", "exception") };
		}
	}

	public int fibonacci(int n) {
		if (n == 1 || n == 0) {
			return n;
		}
		return fibonacci(n - 1) + fibonacci(n - 2);

	}

	@Override
	public double getVersion() {
		return manifest.version();
	}

	@Override
	public String getInstructions() {
		return manifest.instructions();
	}

	@Override
	public String getCategory() {
		return manifest.category();
	}

	@Override
	public String getSkeleton() {
		return "public class Fibonacci {\n\n\tpublic int fibonacci(int n){\n\t\n\t}\n}";
	}

}
