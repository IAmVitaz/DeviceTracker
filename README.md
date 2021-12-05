# DeviceTracker

The Android application simulating the database of devices, can be used as a base for online shop.

Tech stack used:

- MVVM with coroutines and Live Data
- Navigation graph to navigate between fragments
- Retrofit + gson (the mock data is being stored in https://github.com/IAmVitaz/DeviceTracker/blob/main/devices.json which emulate the real API server responce)
- Fresco for image loading
- Espresso for UI tests


The app have an ability filter products by name and type.

User can add devices to favourite, thought the corresponding endpoint have not been added.

The app use ConnectivityManager to improve user experience with unstable internet connection.

Both dark and light themes supported.

UI tests covers the main application workflows.
