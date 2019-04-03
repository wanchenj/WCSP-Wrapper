package HelloWorld;
public class sample {


	public native void helloFromC();



	public sample()
	{
		// do nothing constructor
		listLoadedLibraries();
	}

	public static void main(String[] args){
		System.loadLibrary("sample");
		sample sam_1 = new sample();
		sam_1.helloFromC();
	
	}
}
