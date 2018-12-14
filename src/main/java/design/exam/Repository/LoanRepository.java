package design.exam.Repository;

import design.exam.Model.Loan;
import design.exam.Model.Person;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LoanRepository extends CrudRepository<Loan, Long> {
    List<Loan> findAllByLoanee(Person person);
}
