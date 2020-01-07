import 'package:fancy_on_boarding/fancy_on_boarding.dart';
import 'package:flutter/material.dart';

class OnboadingPage extends StatelessWidget {

  final pageList = [
    PageModel(
      color: const Color(0xFF678FB4),
      heroAssetPath: 'assets/justicehub.png',
      title: Text("Welcome to Github Lite",
      style: TextStyle(
        fontWeight: FontWeight.w800,
        color: Colors.white,
        fontSize: 34.0
      )),
      body: Text("A simple app to make your github experience better",
        textAlign: TextAlign.center,
        style: TextStyle(
          color: Colors.white,
          D:\PROJECTS\Android PROJECTS\github_lite\lib\screens\onboarding.dart
          fontSize: 18.0
        ),
      
      ),
      iconAssetPath: 'assets/justicehub.jpg'
      
     ),
     PageModel(
      color: const Color(0xFF678FB4),
      heroAssetPath: 'assets/scubacat.png',
      title: Text("One place for everything",
      style: TextStyle(
        fontWeight: FontWeight.w800,
        color: Colors.white,
        fontSize: 34.0
      )),
      body: Text("Get all your information on your mobile phone at any time and place",
        textAlign: TextAlign.center,
        style: TextStyle(
          color: Colors.white,
          fontSize: 18.0
        ),
      
      ),
      iconAssetPath: 'assets/scubacat.png'
      
     ),
     PageModel(
      color: const Color(0xFF678FB4),
      heroAssetPath: 'assets/welcometocat.png',
      title: Text("We keep you on the know",
      style: TextStyle(
        fontWeight: FontWeight.w800,
        color: Colors.white,
        fontSize: 34.0
      )),
      body: Text("All the news and pull requests are provided to you. See what your fellow developers are up to",
        textAlign: TextAlign.center,
        style: TextStyle(
          color: Colors.white,
          fontSize: 18.0
        ),
      
      ),
      iconAssetPath: 'assets/welcometocat.png'
      
     ),
     PageModel(
      color: const Color(0xFF678FB4),
      heroAssetPath: 'assets/blactohub.png',
      title: Text("Connect and Share",
      style: TextStyle(
        fontWeight: FontWeight.w800,
        color: Colors.white,
        fontSize: 34.0
      )),
      body: Text("Chat with your fellow developers and discuss all the code related issues.",
        textAlign: TextAlign.center,
        style: TextStyle(
          color: Colors.white,
          fontSize: 18.0
        ),
      
      ),
      iconAssetPath: 'assets/blacktohub.png'
      
     )

  ];







  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: FancyOnBoarding(
        doneButtonText: "Get Started",
        skipButtonText: "Skip",
        pageList: pageList,
        onDoneButtonPressed: () => 
        Navigator.of(context).pushReplacementNamed('/loginPage'),
        onSkipButtonPressed: () => 
        Navigator.of(context).pushReplacementNamed('/loginPage'),
      ),
    );
  }
}