package gdp.racetrack;

public class PerlinNoise
{
	public PerlinNoise(int dimension, double persistance, int octaves, int seed)
	{
		assert dimension > 0;
		mDimension = dimension;
		mPersistance = persistance;
		mOctaves = octaves;
		mSeed = seed;

		mPrimes = new int[mDimension+1];
		mPrimes[0] = 1;
		int i = 1;
		for(int j=57; ; j+=2)
		{
			boolean isPrime = true;
			for(int k=3; k<j/2; k++)
			{
				if(j%k == 0)
				{
					isPrime = false;
					break;
				}
			}
			if(isPrime)
			{
				mPrimes[i] = j;
				if(i == mDimension)
					break;
				i++;
			}
		}
	}
	
	public double getPerlinNoise(double x[])
	{
		assert x != null;
		double total = 0;

		double amplitude = 1;
		double maxAmplitude = 0;

		double tmpX[] = new double[mDimension];
		for(int i=0; i<mDimension; i++)
		{
			tmpX[i] = x[i];
		}

		for(int i=0; i<mOctaves; i++)
		{
			total += interpolateNoise(tmpX) * amplitude;
			maxAmplitude += amplitude;

			for(int j=0; j<mDimension; j++)
			{
				tmpX[j] *= 2;
			}
			amplitude *= mPersistance;
		}
		total = total/maxAmplitude;
		return total;
	}

	private double noise(int x[])
	{
		int n = mSeed * mPrimes[mDimension];
		for(int i=0; i<mDimension; i++)
		{
			n += x[i] * mPrimes[i];
		}
		n = (n<<13) ^ n;
		return ( 1.0 - ( (n * (n * n * 15731 + 789221) + 1376312589) & 0x7fffffff) / 1073741824.0);
	}
	
	private double interpolate(double a, double b, double x)
	{
		double ft = x * 3.1415927;
		double f = (1 - Math.cos(ft)) * 0.5;

		return  a*(1-f) + b*f;
	}
	
	private double interpolateNoise(double x[])
	{
		int intX[] = new int[mDimension];
		double fractionalX[] = new double[mDimension];
		for(int i=0; i<mDimension; i++)
		{
			intX[i] = (int)x[i];
			fractionalX[i] = x[i] - intX[i];
		}

		int numV = 1 << mDimension;
		double v[] = new double[numV];
		for(int i=0; i<numV; i++)
		{
			v[i] = noise(add(intX, i));
		}

		for(int j=0; numV > 1; j++, numV/=2)
		{
			for(int i=0; i<numV/2; i++)
			{
				v[i] = interpolate(v[i*2], v[i*2+1], fractionalX[j]);
			}
		}

		return v[0];
	}
	
	private int[] add(int x[], int i)
	{
		int tmpX[] = new int[mDimension];
		for(int j=0; j<mDimension; j++)
		{
			if((i & (1<<j)) == 0)
			{
				tmpX[j] = x[j];
			}else
			{
				tmpX[j] = x[j] + 1;
			}
		}
		return tmpX;
	}

	private int mDimension;
	private int mOctaves;
	private int mSeed;
	private double mPersistance;

	private int mPrimes[];

};