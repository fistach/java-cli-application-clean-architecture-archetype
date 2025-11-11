def groupId = binding.variables['groupId']

if (groupId == 'test') {
    throw new RuntimeException("Uzyto groupId o wartości 'test'. To powoduje błąd generowania projektu. Wybierz inną wartość.")
}
