import React, { useEffect } from 'react';
import { NativeModules, NativeEventEmitter, View, Text } from 'react-native';

export default function HomeScreen() {
  const { ClickModule } = NativeModules;
  const clickModuleEmitter = new NativeEventEmitter(ClickModule);

  useEffect(() => {
    // Start the foreground service when the component mounts
    ClickModule.startForegroundService();

    // Add the event listener for CapturedText
    const eventListener = clickModuleEmitter.addListener('CapturedText', (capturedText) => {
      console.log('CapturedText:', capturedText);
    });
 

    // Call performClick to simulate a click
    ClickModule.performClick(100, 200)
      .then((result) => {
        console.log('Click performed successfully', result);
      })
      .catch((error) => {
        console.log('Error performing click', error);
      });

    // Cleanup event listener and stop the service when the component unmounts
    return () => {
      clickModuleEmitter.removeAllListeners('CapturedText'); // Remove all listeners
      ClickModule.stopForegroundService();
    };
  }, []); // The empty dependency array ensures this effect runs only once (on mount)

  return (
    <View>
      <Text>Home</Text>
    </View>
  );
}
