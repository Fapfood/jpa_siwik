package fapfood.trzebinski_jpa_practice.ui.editor;

import com.vaadin.data.Binder;
import com.vaadin.data.converter.StringToDoubleConverter;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.TextField;
import fapfood.trzebinski_jpa_practice.model.Customer;
import fapfood.trzebinski_jpa_practice.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;

@SpringComponent
@UIScope
public class CustomerEditor extends AbstractEditor {

    @Autowired
    private CustomerRepository repository;

    private Customer object;

    TextField companyName = new TextField("company name");
    TextField street = new TextField("street");
    TextField city = new TextField("city");
    TextField discount = new TextField("discount");

    Binder<Customer> binder = new Binder<>(Customer.class);

    public CustomerEditor() {
        super();

        addComponents(companyName, street, city, discount, actions);

        binder.bind(companyName, Customer::getCompanyName, Customer::setCompanyName);
        binder.bind(street, Customer::getStreet, Customer::setStreet);
        binder.bind(city, Customer::getCity, Customer::setCity);
        binder.forField(discount).withConverter(new StringToDoubleConverter("")).bind(Customer::getDiscount, Customer::setDiscount);

        // wire action buttons to save, delete and reset
        save.addClickListener(e -> repository.save(object));
        delete.addClickListener(e -> repository.delete(object));
        cancel.addClickListener(e -> editObject(object));
        setVisible(false);
    }

    public final void editObject(Customer o) {
        if (o == null) {
            setVisible(false);
            return;
        }
        final boolean persisted = o.getId() != null;
        if (persisted) {
            // Find fresh entity for editing
            object = repository.findOne(o.getId());
        } else {
            object = o;
        }
        cancel.setVisible(persisted);

        // Bind object properties to similarly named fields
        // Could also use annotation or "manual binding" or programmatically
        // moving values from fields to entities before saving
        binder.setBean(object);

        setVisible(true);

        // A hack to ensure the whole form is visible
        save.focus();
        companyName.selectAll();
    }
}
