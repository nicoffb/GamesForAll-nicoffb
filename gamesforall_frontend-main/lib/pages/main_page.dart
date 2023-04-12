import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:google_fonts/google_fonts.dart';
import 'package:google_nav_bar/google_nav_bar.dart';

import '../blocs/authentication/authentication_bloc.dart';
import '../blocs/authentication/authentication_event.dart';
import '../blocs/productList/product_bloc.dart';
import '../models/user.dart';
import '../repositories/product_repository.dart';
import '../widgets/product_list_widget.dart';

class MainPage extends StatefulWidget {
  User user;

  MainPage({super.key, required this.user});

  @override
  State<MainPage> createState() => _MainPageState(user);
}

class _MainPageState extends State<MainPage> {
  int _selectedIndex = 0;
  User user;
  _MainPageState(this.user);

  @override
  Widget build(BuildContext context) {
    final List<Widget> _pages = [
      SafeArea(minimum: const EdgeInsets.all(2), child: _HomePage()),
      SafeArea(
          minimum: const EdgeInsets.all(2), child: _FavoritePage(user: user)),
      SafeArea(minimum: const EdgeInsets.all(2), child: _MyProducts())
    ];
    final authBloc = BlocProvider.of<AuthenticationBloc>(context);

    return Scaffold(
      appBar: AppBar(
        centerTitle: true,
        toolbarHeight: 50,
        title: ClipRRect(
          child: Container(
            width: 300,
            height: 40,
            decoration: BoxDecoration(
              color: Color.fromARGB(199, 255, 255, 255),
              borderRadius: BorderRadius.all(Radius.elliptical(30, 30)),
            ),
            child: Center(
              child: Text(
                'GAMESFORALL',
                style: GoogleFonts.montserrat(
                    fontWeight: FontWeight.w900,
                    fontSize: 20,
                    color: Color.fromARGB(255, 31, 127, 131)),
              ),
            ),
          ),
        ),
        backgroundColor: Color.fromARGB(255, 51, 124, 183),
        elevation: 2,
      ),
      backgroundColor: Color.fromARGB(255, 230, 230, 230),
      body: _pages[_selectedIndex],
      bottomNavigationBar: Padding(
        padding: const EdgeInsets.symmetric(horizontal: 10.0),
        child: Container(
          decoration: BoxDecoration(
              color: Color.fromARGB(255, 51, 124, 183),
              border: Border.all(
                color: Color.fromARGB(255, 51, 124, 183),
                width: 1,
              ),
              borderRadius: BorderRadius.only(
                  topLeft: Radius.circular(15), topRight: Radius.circular(15))),
          child: Padding(
            padding: const EdgeInsets.symmetric(vertical: 8, horizontal: 20),
            child: GNav(
                selectedIndex: _selectedIndex,
                onTabChange: _navigateBottomBar,
                backgroundColor: Color.fromARGB(255, 51, 124, 183),
                color: Color.fromARGB(150, 255, 255, 255),
                activeColor: Colors.white,
                gap: 10,
                tabBackgroundColor: Color.fromARGB(120, 255, 255, 255),
                padding: EdgeInsets.all(10),
                tabs: [
                  GButton(
                    icon: Icons.home,
                    text: 'Games',
                  ),
                  GButton(
                    icon: Icons.favorite,
                    text: 'Favorites',
                    iconColor: Colors.red,
                  ),
                  GButton(
                    icon: Icons.person,
                    text: 'Profile',
                    iconColor: Color.fromARGB(255, 15, 70, 179),
                  ),
                  GButton(
                    icon: Icons.logout,
                    text: 'Logout',
                    onPressed: () {
                      authBloc.add(UserLoggedOut());
                    },
                  )
                ]),
          ),
        ),
      ),
    );
  }

  void _navigateBottomBar(int index) {
    setState(() {
      _selectedIndex = index;
    });
  }
}

class _HomePage extends StatelessWidget {
  const _HomePage({super.key});

  @override
  Widget build(BuildContext context) {
    return BlocProvider(
      create: (_) {
        final productRepo = new ProductRepository();
        return ProductBloc(
          productRepository: productRepo,
          productType: ProductType.search,
        )..add(GetProductsEvent());
      },
      child: const ProductList(),
    );
  }
}

class _FavoritePage extends StatelessWidget {
  _FavoritePage({super.key, required this.user});
  User user;
  @override
  Widget build(BuildContext context) {
    return BlocProvider(
      create: (_) {
        final productRepo = new ProductRepository();
        return ProductBloc(
          productRepository: productRepo,
          productType: ProductType.favorites,
        )..add(GetProductsEvent());
      },
      child: Scaffold(
        appBar: AppBar(
          backgroundColor: Color.fromARGB(255, 229, 72, 44),
          title: Center(
              child: Text(
            'Productos favoritos',
            style: TextStyle(color: Colors.white),
          )),
        ),
        body: const ProductList(),
      ),
    );
  }
}

class _MyProducts extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return BlocProvider(
      create: (_) {
        final productRepo = new ProductRepository();
        return ProductBloc(
          productRepository: productRepo,
          productType: ProductType.myproducts,
        )..add(GetProductsEvent());
      },
      child: Scaffold(
        appBar: AppBar(
          backgroundColor: Color.fromARGB(255, 15, 70, 179),
          title: Center(
              child: Text(
            'Mis productos',
            style: TextStyle(color: Colors.white),
          )),
        ),
        body: const ProductList(),
      ),
    );
  }
}
