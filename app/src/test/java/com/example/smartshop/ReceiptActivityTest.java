package com.example.smartshop;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.mock;



import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

@RunWith(AndroidJUnit4.class)
public class ReceiptActivityTest {

    @Rule
    public ActivityTestRule<ReceiptActivity> activityRule =
            new ActivityTestRule<>(ReceiptActivity.class, true, false);

    @Mock
    private ReceiptModel mockReceiptModel;

    @Test
    public void testFetchAndDisplayCartItems() {
        // Create a mock of ReceiptModel
        mockReceiptModel = mock(ReceiptModel.class);

        // Launch the activity with the mocked ReceiptModel
        activityRule.launchActivity(new Intent(ApplicationProvider.getApplicationContext(), ReceiptActivity.class));
        ReceiptActivity receiptActivity = activityRule.getActivity();

        // Mock the cart items returned by ReceiptModel
        List<String> mockItems = Arrays.asList("Item 1", "Item 2", "Item 3");

        // Simulate data received
        doAnswer(invocation -> {
            ReceiptModel.OnCartDataReceivedListener listener = invocation.getArgument(0);
            listener.onDataReceived(mockItems);
            return null;
        }).when(mockReceiptModel).fetchCartItems(any(ReceiptModel.OnCartDataReceivedListener.class));

        // Trigger the fetch and display method without arguments
        receiptActivity.fetchAndDisplayCartItems(); // Call without arguments

        // Check if the receiptTextView displays the correct text
        onView(withId(R.id.receiptTextView)).check(matches(withText("Item 1\nItem 2\nItem 3\n")));
    }

    @Test
    public void testOnDataFetchError() {
        // Launch the activity
        activityRule.launchActivity(new Intent(ApplicationProvider.getApplicationContext(), ReceiptActivity.class));
        ReceiptActivity receiptActivity = activityRule.getActivity();

        // Simulate an error
        Exception mockException = new Exception("Network Error");

        // Manually trigger the onDataFetchError
        receiptActivity.fetchAndDisplayCartItems(); // Call without arguments
        // Note: Handle your error simulation logic here if necessary
    }
}
