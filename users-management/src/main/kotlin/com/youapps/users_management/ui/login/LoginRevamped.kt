package com.youapps.users_management.ui.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.youapps.onlybeans.designsystem.R


// Define missing colors consistent with OnlyBeans design system
val CoffeeLight = Color(0xFFF0ECEC)
val CoffeeDeep = Color(0xFFb45309)
val CoffeeBorder = Color(0xFFB9B9B9)
val TextSecondary = Color(0xFF8D8B8B)


@Preview
@Composable
fun CoffeeLoginScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CoffeeLight)
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        // Logo Icon
        Box(
            modifier = Modifier
                .size(80.dp)
                .background(CoffeeDeep, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_app_logo_icon), // Replace with your coffee icon
                contentDescription = "App Logo",
                tint = Color.White,
                modifier = Modifier.size(40.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Welcome Back",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF5D2E17)
        )

        Text(
            text = "Sign in to your coffee account",
            color = TextSecondary,
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Email Field
        LoginTextField(label = "Email", placeholder = "your@email.com")

        Spacer(modifier = Modifier.height(16.dp))

        // Password Field
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Password", fontWeight = FontWeight.SemiBold, color = Color(0xFF5D2E17))
                TextButton(onClick = { /* Handle Forgot */ }) {
                    Text("Forgot?", color = CoffeeDeep)
                }
            }
            OutlinedTextField(
                value = "",
                onValueChange = {},
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Enter your password") },
                shape = RoundedCornerShape(12.dp),
                trailingIcon = {
                    Icon(imageVector = ImageVector.vectorResource(R.drawable.ic_app_logo_icon), contentDescription = null, tint = CoffeeDeep)
                },
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = CoffeeBorder,
                    focusedBorderColor = CoffeeDeep
                )
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Sign In Button
        Button(
            onClick = { /* Handle Sign In */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = CoffeeDeep),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Sign In", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Divider
        Row(verticalAlignment = Alignment.CenterVertically) {
            HorizontalDivider(modifier = Modifier.weight(1f), color = CoffeeBorder)
            Text(
                " or continue with ",
                modifier = Modifier.padding(horizontal = 8.dp),
                color = TextSecondary,
                fontSize = 14.sp
            )
            HorizontalDivider(modifier = Modifier.weight(1f), color = CoffeeBorder)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Social Buttons
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            SocialButton(icon = R.drawable.ic_google_icon, text = "Google", modifier = Modifier.weight(1f))
            SocialButton(icon = R.drawable.ic_pinterest_icon, text = "Pinterest", modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Sign Up Footer
        Row {
            Text("Don't have an account? ", color = TextSecondary)
            Text(
                "Sign up",
                color = CoffeeDeep,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable { /* Navigate to Sign Up */ }
            )
        }
    }
}

@Composable
fun LoginTextField(label: String, placeholder: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = label, fontWeight = FontWeight.SemiBold, color = Color(0xFF5D2E17))
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = "",
            onValueChange = {},
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(placeholder) },
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = CoffeeBorder,
                focusedBorderColor = CoffeeDeep
            )
        )
    }
}

@Composable
fun SocialButton(icon: Int, text: String, modifier: Modifier = Modifier) {
    OutlinedButton(
        onClick = { /* Handle Social Login */ },
        modifier = modifier.height(50.dp),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, CoffeeBorder)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = text, color = Color(0xFF5D2E17))
        }
    }
}