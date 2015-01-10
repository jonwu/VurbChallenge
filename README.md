Vurb Challenge
==============
This is a simple native app that displays geolocation and list of cards.

Requirements
=============
- Minimum SDKVersion 15
- Ensure Network and GPS are ON

Getting Started
===============
- Import Project to Android Studio
- Build and Run

Features
=========
- Splash Page with parellel tasks (Geolocation and getting json)
- Cards Page with **infinite scrolling** (Reload same data).

Optimizations
=============
- Storing views in viewholders
- Running tasks in parellel
- Caching images

Creating New type of cards
==========================
*For purposes of scalibility, here are steps to add new types in the future.*

- Create a new model for this type. Optional to extend generic Card type class
- Create an xml page and add view in CardViews.java to display a custom design.
- Add view to list by indexing your type in ListViewAdapter.

Libraries
=========
- Universal Image Loader v1.3