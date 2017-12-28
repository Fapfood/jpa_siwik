package fapfood.trzebinski_jpa_practice.ui.editor;

import com.vaadin.data.Binder;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.DateField;
import com.vaadin.ui.TextField;
import fapfood.trzebinski_jpa_practice.model.BusinessTransaction;
import fapfood.trzebinski_jpa_practice.model.Product;
import fapfood.trzebinski_jpa_practice.repository.BusinessTransactionRepository;
import fapfood.trzebinski_jpa_practice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

@SpringComponent
@UIScope
public class TransactionEditor extends AbstractEditor {

    @Autowired
    private BusinessTransactionRepository repository;

    @Autowired
    private ProductRepository productRepository;

    private BusinessTransaction object;

    DateField date = new DateField("date");

    TextField sales = new TextField("sales");

    Binder<BusinessTransaction> binder = new Binder<>(BusinessTransaction.class);

    public TransactionEditor() {
        super();

        addComponents(date, sales, actions);
        binder.bind(date, BusinessTransaction::getDate, BusinessTransaction::setDate);
        binder.forField(sales).bind(businessTransaction -> businessTransaction.getSales().toString(),
                (businessTransaction, s) -> {
                    Set<Product> productSet = new HashSet<>();
                    for (String str : s.substring(1, s.length() - 1).split(", ")) {
                        Long id = Long.parseLong(str);
                        Product product = productRepository.findOne(id);
                        productSet.add(product);
                    }
                    businessTransaction.setSales(productSet);
                });

        // wire action buttons to save, delete and reset
        save.addClickListener(e -> repository.save(object));
        delete.addClickListener(e -> repository.delete(object));
        cancel.addClickListener(e -> editObject(object));
        setVisible(false);
    }

    public final void editObject(BusinessTransaction o) {
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
    }
}
