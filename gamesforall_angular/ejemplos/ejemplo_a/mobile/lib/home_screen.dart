import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:front/profile/profile_screen.dart';

import 'auth/auth_bloc.dart';
import 'login/login_screen.dart';
import 'landing/landing_screen.dart';

List<Widget> _widgetOptions = <Widget>[
  BlocBuilder<AuthenticationBloc, AuthenticationState>(
      builder: (context, state) {
    if (state is AuthenticationNotAuthenticated) {
      return LoginScreen();
    } else if (state is AuthenticationAuthenticated) {
      return ProfileScreen();
    }
    return Text("Loading");
  }),
  LandingScreen(),
  Center(
    child: Text(
      '¡Próximamente!',
    ),
  )
];

class HomeScreen extends StatefulWidget {
  HomeScreen({super.key});
  HomeScreen.withIndex({super.key, required this.indexInitial});
  int indexInitial = 1;

  @override
  State<HomeScreen> createState() => _HomeScreenState(indexInitial);
}

class _HomeScreenState extends State<HomeScreen> {
  late int _selectedIndex;

  _HomeScreenState(int indexInitial) {
    _selectedIndex = indexInitial;
  }

  void _onItemTapped(int index) {
    setState(() {
      _selectedIndex = index;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: _widgetOptions.elementAt(_selectedIndex),
      bottomNavigationBar: BottomNavigationBar(
        items: <BottomNavigationBarItem>[
          BottomNavigationBarItem(
            icon: Icon(Icons.person),
            label: 'Perfil',
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.food_bank_rounded),
            label: 'Pedir',
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.shopping_bag),
            label: 'Carrito',
          ),
        ],
        currentIndex: _selectedIndex,
        selectedItemColor: Colors.red.shade700,
        onTap: _onItemTapped,
      ),
    );
  }
}
