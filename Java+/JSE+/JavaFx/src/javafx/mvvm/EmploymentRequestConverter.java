package javafx.mvvm;

class EmploymentRequestConverter {
    public EmploymentRequest toEmploymentRequest(EmploymentRequestViewModel viewModel) {
        return new EmploymentRequest(
                viewModel.getName(),
                viewModel.getPosition(),
                viewModel.getAnnualSalary()
        );
    }
}


