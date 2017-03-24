import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Random;

import org.powerbat.executor.Result;
import org.powerbat.interfaces.Manifest;
import org.powerbat.interfaces.ProjectSet;

@Manifest(category = "logic", instructions = "Return the sum of parameters a and b in method \"add\"", version = 1.0)
public class SimpleAdditionRunner implements ProjectSet {

	//adds two numbers
	
	private static final Manifest manifest = SimpleAdditionRunner.class.getAnnotation(Manifest.class);
	
	public double getVersion(){
		return manifest.version();
	}
	
	public String getInstructions(){
		return manifest.instructions();
	}
	
	private static final Random random = new Random(); //random number generator

	@Override
	public Result[] getResults(Class<?> c) {
		try{
			final Object instance = c.newInstance();
			final Method m = c.getMethod("add", int.class, int.class);

			final int count = 10;
			final ArrayList<Result> results = new ArrayList<Result>();
			for(int i = 0; i < count; i++){//just iterate ten times
				final int x = -3000 + random.nextInt(6000); //random between -3000 and 5999
				final int y = -3000 + random.nextInt(6000);
				final int correct = x + y;
				final int answer = (Integer) m.invoke(instance, x, y);
				
				results.add(new Result(answer, correct, x, y));
			}
			return results.toArray(new Result[results.size()]);
		}catch(Exception e){
			e.printStackTrace();
		}
		return new Result[]{};
	}
	
	public String getSkeleton() {
		return "public class SimpleAddition {\n    public int add(int x, int y){\n        \n    }\n}";
	}

	@Override
	public String getCategory() {
		return manifest.category();
	}

}
