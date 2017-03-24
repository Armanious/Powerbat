import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Random;

import org.powerbat.executor.Result;
import org.powerbat.interfaces.Manifest;
import org.powerbat.interfaces.ProjectSet;

@Manifest(category = "Logic", instructions = "Given parameters boolean lowGas and boolean lowOil, using the getWarning method, return 0 if you have no issues, 1 if you have lowOil, 2 if you have lowGas, and 3 if you have both.", version = 1.0)
public class CarTroubleRunner implements ProjectSet {

	private static Manifest properties = CarTroubleRunner.class
			.getAnnotation(Manifest.class);
	private static Random random = new Random();
	
	@Override
	public Result[] getResults(Class<?> classToTest) {
		try {
			final Object instance = classToTest.newInstance();
			final Method method = classToTest.getMethod("getWarning",
					boolean.class, boolean.class);
			final ArrayList<Result> results = new ArrayList<Result>();
			
			final ArrayList<boolean[]> sets = new ArrayList<boolean[]>();
			sets.add(new boolean[]{true, true});
			sets.add(new boolean[]{true, false});
			sets.add(new boolean[]{false, true});
			sets.add(new boolean[]{false, false});
			while(!sets.isEmpty()){
				int j = random.nextInt(sets.size());
				boolean[] b = sets.remove(j);
				results.add(new Result(b[0] == b[1] ? (b[0] ? 3 : 0) : (b[0] ? 2 : 1), method.invoke(instance, b[0], b[1]), b[0], b[1]));
			}
			return results.toArray(new Result[4]);
		} catch (Exception e) {
			return new Result[] {};
		}
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
		return "public class CarTrouble {\n\n    public int getWarning(boolean lowGas, boolean lowOil){\n        \n    }\n}";
	}

	@Override
	public String getCategory() {
		return properties.category();
	}

}
