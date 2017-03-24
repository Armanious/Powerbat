import java.lang.reflect.Method;
import java.util.Random;

import org.powerbat.executor.Result;
import org.powerbat.interfaces.Manifest;
import org.powerbat.interfaces.ProjectSet;

@Manifest(category = "AP", instructions = "Return the nth prime number in method getNthPrim(int n).\n1-1000 inclusive\ngetNthPrime(1) = 2, getNthPrime(2) = 3, getNthPrime(3) = 5", version = 1.0)
public class PrimeNumberRunner implements ProjectSet {

	private static final Random random = new Random();
	private static final Manifest Manifest = PrimeNumberRunner.class.getAnnotation(Manifest.class);
	private static final int[] primes = getPrimes();

	@Override
	public Result[] getResults(Class<?> classToTest) {
		try{
			final Method method = classToTest.getMethod("getNthPrime", int.class);
			final Result[] results = new Result[10];
			for(int i = 0; i < 10; i ++){
				int param = random.nextInt(1000);
				results[i] = new Result(method.invoke(classToTest.newInstance(), param + 1), primes[param], param + 1);
			}
			return results;
		}catch(Exception e){
			e.printStackTrace();
			return new Result[]{new Result("Error", "executing"), new Result("possibly", "xxception")};
		}
	}

	@Override
	public double getVersion() {
		return Manifest.version();
	}

	@Override
	public String getInstructions() {
		return Manifest.instructions();
	}

	@Override
	public String getSkeleton() {
		return "public class PrimeNumber {\n    \n    public int getNthPrime(int n){\n        \n    }\n    \n}";
	}

	private static int[] getPrimes(){
		final int[] primes = new int[1000];
		primes[0] = 2;
		primes[1] = 3;
		primes[2] = 5;
		for(int idx = 3; idx < primes.length;){
			primeSearch: 
				for(int i = primes[idx - 1] + 2;; i += 2){ //use last prime as a starting point, but add 2 so it stays odd
					for(int j = 0; j < idx; j++){
						if(i % primes[j] == 0){ //check if any of the previous primes are a factor of current number
							//if no previous primes are a factor, then current number is a prime
							continue primeSearch;
						}
					}
					//if execution reaches here, no previous are a factor, so set the next prime number and increase index
					if(idx == primes.length)
						break;
					primes[idx++] = i;
				}
		}
		return primes;
	}

	@Override
	public String getCategory() {
		return Manifest.category();
	}

}
