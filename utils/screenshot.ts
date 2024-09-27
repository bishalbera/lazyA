import { captureRef } from "react-native-view-shot"

export const takeScreenshot = (viewRef, callback) => 
    {
        captureRef(viewRef, 
            {
                format: 'png',
                quality: 0.8,
            }
        ).then(
            uri=> 
                {
                    console.log('screenshot saved to:', uri)
                    if(callback)
                        {
                            callback(uri)
                        }
                },
                error=> console.log('screenshot failed', error)
        )
    }
    
    // ------Usage_Example-------
    /*
    import React, { useRef } from 'react';
    import { View, Button } from 'react-native';
    import { takeScreenshot } from './screenshotHelper'; // Path to your helper

    const ScreenshotExample = () => {
    const viewRef = useRef();

    const handleTaskStart = () => {
        // Take a screenshot when a task starts
        takeScreenshot(viewRef, (uri) => {
        console.log('Screenshot captured for task start:', uri);
        });
    };

    const handleTaskComplete = () => {
        // Take a screenshot when a task completes
        takeScreenshot(viewRef, (uri) => {
        console.log('Screenshot captured for task complete:', uri);
        });
    };

    return (
        <View ref={viewRef}>
        <Button title="Start Task" onPress={handleTaskStart} />
        <Button title="Complete Task" onPress={handleTaskComplete} />
        </View>
    );
    };

    export default ScreenshotExample;

     */