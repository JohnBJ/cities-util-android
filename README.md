# cities-util-android
CIties-Util-Android wraps city endpoint(http://www.mocky.io/v2/5b7e8bc03000005c0084c210) using Volley(https://developer.android.com/training/volley) to make network calls. It allows developers to make the following requests:

    1. Request a list of cities.
    2. Request a particular city.
    3. Request a list of malls in a city.
    4. Request a particular mall in a city.
    5. Request a list of shops in a mall.
    6. Request a particular shop in a mall.
    7. Request a list of shops in a city.
    8. Request the last valid data when offline.
    
# How to add (gradle)

If you are using gradle: Step1: Add it in your root build.gradle at the end of repositories

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
Step2: Add the dependency

	dependencies {
	        implementation 'com.github.JohnBJ:cities-util-android:Tag'
	}
  
Otherwise you have to use library directly in your project.
# Usage

Check internet connectivity, if there is no connectivity use last valid data

    private CitiesApiController citiesApiController;
    private List<City> cityList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        citiesApiController = new CitiesApiController(this);
        if(checkInternetConnection()) {
            citiesApiController.getListOfCities(new VolleyResponseListener() {
                @Override
                public void onResponse(ArrayList<City> cities) {
                    //Do something
                    cityList = cities;
                    citiesApiController.saveData();
                }

                @Override
                public void onError(String error) {
                    //Do something
                }
            });
        } else {
            //Get data from shared prefs
            citiesApiController.getLastValidData();
            cityList = citiesApiController.getListOfCitiesFromSharedPref();
        }
    }
