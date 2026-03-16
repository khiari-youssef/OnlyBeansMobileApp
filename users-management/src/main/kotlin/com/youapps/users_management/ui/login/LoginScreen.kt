package com.youapps.users_management.ui.login

import OBButtonContainedPrimary
import OBEmailTextField
import OBPasswordTextField
import OBTextButton
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.focusGroup
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.youapps.onlybeans.designsystem.R
import com.youapps.onlybeans.designsystem.R as DesignR
import com.youapps.onlybeans.users_management.R as UserR


val CoffeeBorder = Color(0xFFB9B9B9)
val TextSecondary = Color(0xFF8D8B8B)



@Composable
fun LoginScreen(
    modifier: Modifier,
    screenState : LoginUIStateHolder,
    onEmailChanged : (String)-> Unit,
    onPasswordChanged : (String) -> Unit,
    onSignUpClicked : ()-> Unit,
    onGoogleSignInClicked : ()-> Unit,
    onPInterestSignInClicked : ()-> Unit,
    onSignInClicked : ()-> Unit,
    onForgotPasswordClicked : ()-> Unit,
) {
    ConstraintLayout(
        modifier = modifier
    ) {
        val (header, form, buttons, footer) = createRefs()
        OBLoginHeader(
            modifier = Modifier.constrainAs(header){
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )
        OBLoginForm(
            modifier = Modifier.constrainAs(form){
                top.linkTo(header.bottom,32.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            email = screenState.loginEmail.value,
            password = screenState.loginPassword.value,
            onEmailChanged = onEmailChanged,
            onPasswordChanged = onPasswordChanged,
            onForgotPasswordClicked = onForgotPasswordClicked
        )
        OBLoginButtonsSection(
            modifier = Modifier
                .constrainAs(buttons){
                      top.linkTo(form.bottom,20.dp)
                      start.linkTo(parent.start)
                      end.linkTo(parent.end)
                }
                .fillMaxWidth(),
            onGoogleSignInClicked = onGoogleSignInClicked,
            onPInterestSignInClicked = onPInterestSignInClicked,
            onSignInClicked = onSignInClicked,
            isCredentialLoginEnabled = screenState.loginEmail.value.isNotBlank() && screenState.loginPassword.value.isNotBlank()
        )
        OBLoginFooterSection(
            modifier = Modifier .constrainAs(footer){
                top.linkTo(buttons.bottom,20.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                 bottom.linkTo(parent.bottom)
            },
            onSignUpClicked = onSignUpClicked
        )
    }
}


@Composable
fun OBLoginFooterSection(
    modifier: Modifier = Modifier,
    onSignUpClicked: () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically)
    ) {
        HorizontalDivider(modifier = Modifier.weight(1f), color = CoffeeBorder)
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
        ) {
            Text(stringResource(UserR.string.login_no_account), color = TextSecondary)
            OBTextButton(
                text = stringResource(UserR.string.login_sign_up_action),
                onClick = onSignUpClicked,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}


@Composable
fun OBLoginButtonsSection(
    modifier: Modifier = Modifier,
    isCredentialLoginEnabled : Boolean,
    onGoogleSignInClicked: () -> Unit,
    onPInterestSignInClicked: () -> Unit,
    onSignInClicked: () -> Unit
) {

   Column(
       modifier = modifier,
       horizontalAlignment = Alignment.CenterHorizontally,
       verticalArrangement = Arrangement.spacedBy(20.dp,Alignment.CenterVertically)
   ) {
       OBButtonContainedPrimary(
           modifier = Modifier.fillMaxWidth(),
           isEnabled = isCredentialLoginEnabled,
           text = stringResource(id = DesignR.string.login),
           size = OBButtonSize.Large,
           onClick = onSignInClicked
       )
       Row(verticalAlignment = Alignment.CenterVertically) {
           HorizontalDivider(modifier = Modifier.weight(1f), color = CoffeeBorder)
           Text(
               stringResource(id = UserR.string.login_social_divider),
               modifier = Modifier.padding(horizontal = 8.dp),
               color = TextSecondary,
               fontSize = 14.sp
           )
           HorizontalDivider(modifier = Modifier.weight(1f), color = CoffeeBorder)
       }
       Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
           SocialButton(
               icon = DesignR.drawable.ic_google_icon,
               text = stringResource(id = UserR.string.login_google),
               onClick = onGoogleSignInClicked,
               modifier = Modifier.weight(1f)
           )
           SocialButton(
               icon = DesignR.drawable.ic_pinterest_icon,
               text = stringResource(id = UserR.string.login_pinterest),
               onClick = onPInterestSignInClicked,
               modifier = Modifier.weight(1f)
           )
       }
   }
}

 @Composable
private fun OBLoginHeader(modifier: Modifier = Modifier) {
     Column(
         modifier = modifier,
         horizontalAlignment = Alignment.CenterHorizontally,
         verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.Top)
     ) {
         Box(
             modifier = Modifier
                 .size(80.dp)
                 .background(MaterialTheme.colorScheme.primary, CircleShape),
             contentAlignment = Alignment.Center
         ) {
             Icon(
                 painter = painterResource(id = DesignR.drawable.ic_coffee_bean),
                 contentDescription = stringResource(id = UserR.string.content_description_app_logo),
                 tint = Color.White,
                 modifier = Modifier.size(40.dp)
             )
         }
         Spacer(modifier = Modifier.height(12.dp))
         Text(
             text = stringResource(id = UserR.string.login_welcome_back),
             style = MaterialTheme.typography.headlineMedium,
             fontWeight = FontWeight.Bold,
             color = Color(0xFF5D2E17)
         )
         Text(
             text = stringResource(id = UserR.string.login_subtitle),
             color = TextSecondary,
             style = MaterialTheme.typography.bodyLarge
         )
     }
}


@Composable
private fun OBLoginForm(
    modifier: Modifier = Modifier,
    email : String,
    password : String,
    onEmailChanged : (String)-> Unit,
    onPasswordChanged : (String) -> Unit,
    onForgotPasswordClicked: () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top)
    ) {
        OBEmailTextField(
            modifier = Modifier
                .focusGroup()
                .fillMaxWidth()
                .wrapContentHeight()
                .semantics {
                    contentDescription = "LoginEmailTextField"
                },
            text = email,
            isEnabled = true,
            rightIconRes = R.drawable.ic_clear,
            keyboardActions = KeyboardActions.Default,
            onRightIconResClicked = {
                onEmailChanged("")
            },
            onEmailChanged = onEmailChanged
        )
        OBPasswordTextField(
            modifier = Modifier
                .focusGroup()
                .fillMaxWidth()
                .wrapContentHeight()
                .semantics {
                    contentDescription = "LoginPasswordTextField"
                },
            password = password,
            label = stringResource(id = R.string.password_label),
            placeholder = stringResource(id = R.string.password_placeholder),
            keyboardActions = KeyboardActions.Default,
            isEnabled = true,
            onPasswordChanged = onPasswordChanged
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OBTextButton(
                text = stringResource(id = UserR.string.login_forgot_password),
                onClick = onForgotPasswordClicked,
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}

@Composable
fun SocialButton(icon: Int, text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    OutlinedButton(
        onClick = onClick,
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
