# Reactive MVP
This app is nothing more than an example of how to create an MVP (Model View Presenter) application using RX (Reactive).

MVP breaks down an application into more testable components keeping your internal business objects and data separate from
the external UI or external data.

The app simply loads a list of Game Of Throne Characters from a JSON hosting website and displays that information to the
screen in a list and grid format. 

![Alt text](/doc/list_ui.png?raw=true "JSON")
![Alt text](/doc/grid_ui.png?raw=true "JSON")

The following two end points will be discussed:

1. https://api.myjson.com/bins/18vxt
2. https://api.myjson.com/bins/2lsth

![Alt text](/doc/json_data_screenshot.png?raw=true "JSON")

The application architecture is as followed: 
![Alt text](/doc/Reactive_MVP.png?raw=true "App MVP Architecture")

## Usage

Apache License 2.0. Free to use & distribute.

### Build Flavors

Only the original build flavors release / debug. Use Debug since this
not an app for the google play store.

### Tests

This project supports the following type of tests:

1. Java Unit Test
2. Robolectric(There are none - and that was intentional to show how you can test without this when you use MVP)

To run all unit tests:

./gradlew testDebugUnitTest
