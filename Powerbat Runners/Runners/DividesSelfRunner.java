import java.lang.reflect.Method;
import java.util.Random;

import org.powerbat.executor.Result;
import org.powerbat.interfaces.Manifest;
import org.powerbat.interfaces.ProjectSet;

@Manifest(category = "AP", instructions = "Return true if a number is divisible by each containing digit.", version = 0.2d)
public class DividesSelfRunner implements ProjectSet {

	Manifest manifest = getClass().getAnnotation(Manifest.class);

	@Override
	public Result[] getResults(Class<?> clazz) {
		try {
			Method method = clazz.getMethod("canDivide", int.class);
			Random ran = new Random();
			Result[] results = new Result[10];
			for (int i = 0; i < results.length; i++) {
				int selected = ran.nextInt(501) + ran.nextInt(500);
				results[i] = new Result(method.invoke(clazz.newInstance(), selected), canDivide(selected), selected);
			}
			return results;
		} catch (Exception e) {
			return null;
		}
	}

	private boolean canDivide(int n) {
		if (n < 100)
			if (n % 10 == 0)
				return n % (n / 10) == 0;
			else
				return n % (n / 10) == 0 && n % (n % 10) == 0;
		else if (n % 10 == 0)
			return false;
		return n % (n / 100) == 0 && n % ((n / 10) % 10) == 0
				&& n % (n % 10) == 0;
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
		return "public class DividesSelf {\n\n\tpublic boolean canDivide(int num){\n\t\n\t}\n}";
	}

}
