/*
@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OBDateRangePicker(
    modifier: Modifier = Modifier,
    dateValidator: (dateInMS: Long) -> Boolean = { true },
    yearValidator: (year: Int) -> Boolean = { true }
) {

    val colors = DatePickerDefaults.colors(
        containerColor = Alabaster,
        todayContentColor = RoseEbony,
        todayDateBorderColor = RoseEbony,
        selectedDayContainerColor = RoseEbony,
        selectedDayContentColor = if (isSystemInDarkTheme()) Color(0xFFB6B6B6)
        else Color.White,
        selectedYearContainerColor = Alabaster,
        dayInSelectionRangeContainerColor = Licorice,
        dayInSelectionRangeContentColor = MaterialTheme.colorScheme.surfaceVariant,
        dayContentColor = MaterialTheme.colorScheme.onBackground
    )
    val currentYear = LocalDateTime.now().year
    val state = rememberDateRangePickerState(
        yearRange = currentYear - 1..currentYear + 1,
        initialSelectedStartDateMillis = null,
        initialSelectedEndDateMillis = null,
        selectableDates = object : SelectableDates {

            override fun isSelectableDate(utcTimeMillis: Long) = dateValidator(utcTimeMillis)

            override fun isSelectableYear(year: Int) = yearValidator(year)
        }
    )
    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        DateRangePicker(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxSize(),
            state = state,
            colors = colors
        )

    }
}
 */