# Issue with Navigation backstack and BottomView

Linked issue : https://issuetracker.google.com/issues/194301895

## App

This app show a 2 destinations BottomNavigationView. It uses Navigation 2.4.0-alpha05

![Home](https://raw.githubusercontent.com/NitroG42/NavigationMultibackStackInnerNav/master/screens/home.png)

- The first screen (Home) contains a label and a button Notifications
- The second screen (Notifications) contains just a label

When pressing the Notifications **button** (not the menu from the BottomNav), the click will simulate the usage of a NavDeepLink
(use case : clicking on a notification to launch the app directly on the Notifications screen) :

```
NavDeepLinkBuilder(requireContext())
    .setGraph(R.navigation.mobile_navigation)
    .setDestination(R.id.navigation_notifications)
    .createTaskStackBuilder().startActivities()
```

## Issue

When using the Notifications button, the screen is restarted (we launch a fresh/new activity so this is normal) but pressing the Home menu
will select it, **without changing the current screen**.

![Bug](https://raw.githubusercontent.com/NitroG42/NavigationMultibackStackInnerNav/master/screens/bug.png)

If we press the Back button, it will pop to the startDestination of the graph (Home) and this time it will work.

## Similar Issues

We can have the exact same issue when moving to Navigation using the following simple code :

```
findNavController().navigate(R.id.navigation_notifications
```

The simple solution is to enable saveInstance/restore :

```
findNavController().navigate(R.id.navigation_notifications, null, navOptions {
    popUpTo(findNavController().graph.findStartDestination().id) {
        saveState = true
        inclusive = false
    }
})
```






