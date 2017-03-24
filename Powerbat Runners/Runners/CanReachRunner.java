import java.lang.reflect.Method;
import java.util.Random;

import org.powerbat.executor.Result;
import org.powerbat.interfaces.Manifest;
import org.powerbat.interfaces.ProjectSet;

@Manifest(category = "Logic", instructions = "Return true if you can reach the goal (Without going over) by adding large sticks (worth 5) and small sticks (worth 1). \nYou only have a number of each of these given in the parameters.", version = 1.0)
public class CanReachRunner implements ProjectSet{

	Manifest manifest = getClass().getAnnotation(Manifest.class);
	
	@Override
	public Result[] getResults(Class<?> clazz) {
		try{
			Method method = clazz.getMethod("canReach", int.class, int.class, int.class);
			Random ran = new Random();
			Result[] results = new Result[10];
			for(int i = 0; i < 10; i++){
				int small = ran.nextInt(15), large = ran.nextInt(5), goal = (small + large * 5) - 3 + ran.nextInt(6);
				results[i] = new Result(method.invoke(clazz.newInstance(), small, large, goal), (goal > small + large * 5) ? false : goal % 5 <= small, small, large, goal);
			}
			return results;
		} catch (Exception e){
			return new Result[]{};
		}
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
		return "public class CanReach {\n\n\tpublic boolean canReach(int small, int large, int goal){\n\t\n\t}\n}";
	}

}
