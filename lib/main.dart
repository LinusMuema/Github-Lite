import 'package:flutter/material.dart';
import 'package:github_lite/screens/login_page.dart';
import 'package:github_lite/screens/onboarding.dart';

void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'GitHub Lite',
      debugShowCheckedModeBanner: false,
       routes: {

    '/': (context) => OnboardingPage(),
    // When navigating to the "/second" route, build the SecondScreen widget.
    '/loginPage': (context) => LoginPage(),
  },
    );
  }
}
