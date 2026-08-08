import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:native_bridge/native_bridge.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(home: TestPage());
  }
}

class TestPage extends StatefulWidget {
  const TestPage({super.key});

  @override
  State<TestPage> createState() => _TestPageState();
}

class _TestPageState extends State<TestPage> {
  late final NativeBridge _nativeBridge;

  @override
  void initState() {
    super.initState();
    _nativeBridge = NativeBridge();
  }

  void _getNumberFromSim() async {
    final result = await _nativeBridge.getPhoneHint();

    debugPrint('reuslt -> $result');
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text('Test Page')),
      body: Center(
        child: Padding(
          padding: const EdgeInsets.all(16.0),
          child: Column(
            mainAxisAlignment: .center,
            spacing: 16,
            children: [
              ElevatedButton(
                onPressed: _getNumberFromSim,
                child: Text('Get Number from sim'),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
