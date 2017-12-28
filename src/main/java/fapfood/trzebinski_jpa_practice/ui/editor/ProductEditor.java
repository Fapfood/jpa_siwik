package fapfood.trzebinski_jpa_practice.ui.editor;

import com.vaadin.data.Binder;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.TextField;
import fapfood.trzebinski_jpa_practice.model.Product;
import fapfood.trzebinski_jpa_practice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;

@SpringComponent
@UIScope
public class ProductEditor extends AbstractEditor {

    @Autowired
    private ProductRepository repository;

    private Product object;

    TextField productName = new TextField("product name");
    TextField unitsInStock = new TextField("unit in stock");

    Binder<Product> binder = new Binder<>(Product.class);

    public ProductEditor() {
        super();

        addComponents(productName, unitsInStock, actions);

        binder.bind(productName, Product::getProductName, Product::setProductName);
        binder.forField(unitsInStock).withConverter(new StringToIntegerConverter("")).bind(Product::getUnitsInStock, Product::setUnitsInStock);

        // wire action buttons to save, delete and reset
        save.addClickListener(e -> repository.save(object));
        delete.addClickListener(e -> repository.delete(object));
        cancel.addClickListener(e -> editObject(object));
        setVisible(false);
    }

    public final void editObject(Product o) {
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
        productName.selectAll();
    }
}
