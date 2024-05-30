Java.perform(function() {
    // Hook into the DynamicAnalysisActivity class
    var DynamicAnalysisActivity = Java.use("com.example.ctfapp.DynamicAnalysisActivity");

    // Hook the onCreate method of the activity
    DynamicAnalysisActivity.onCreate.implementation = function(savedInstanceState) {
        // Call the original onCreate method
        this.onCreate(savedInstanceState);
        console.log("DynamicAnalysisActivity.onCreate called");

        // Find the input field and set the secret code
        try {
            var EditText = Java.use("android.widget.EditText");
            var inputFieldId = Java.use("com.example.ctfapp.R$id").input_field.value;
            var inputField = this.findViewById(inputFieldId);
            if (inputField) {
                inputField.setText("CTF2024");
                console.log("Set input field to CTF2024");

                // Find the button and click it programmatically
                var Button = Java.use("android.widget.Button");
                var checkButtonId = Java.use("com.example.ctfapp.R$id").check_button.value;
                var checkButton = this.findViewById(checkButtonId);
                if (checkButton) {
                    checkButton.performClick();
                    console.log("Programmatically clicked the check button");
                } else {
                    console.error("Could not find check button with ID: " + checkButtonId);
                }
            } else {
                console.error("Could not find input field with ID: " + inputFieldId);
            }
        } catch (e) {
            console.error("Error setting input field or clicking button: " + e.message);
        }
    };
});
