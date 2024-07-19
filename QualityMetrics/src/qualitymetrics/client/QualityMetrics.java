package qualitymetrics.client;

import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

import qualitymetrics.shared.Metrics;

public class QualityMetrics implements EntryPoint {
	
	private final DBAsync DBService = GWT.create(DB.class);
	
	public void onModuleLoad() {	
		// Create Main Container
        VLayout mainLayout = new VLayout();
        mainLayout.setWidth100();
        mainLayout.setHeight100();
        mainLayout.setStyleName("mainLayout");
        
        // Create DynamicForm
        DynamicForm form1 = new DynamicForm();
        form1.setWidth(300);
        form1.setHeight(25);
        form1.setNumCols(4);
        
        // Create SelectItem Applications
        SelectItem selectItemApps = new SelectItem();
        selectItemApps.setTitle("Application");
        selectItemApps.setType("text");
        selectItemApps.setValueMap("App 1", "App 2", "App 3","App 4");
        
        // Create SelectItem Versions
        SelectItem selectItemVersions = new SelectItem();
        selectItemVersions.setTitle("Version");
        selectItemVersions.setType("text");
        
        // Add ChangedHandler to First SelectItem Applications
        selectItemApps.addChangedHandler(new ChangedHandler() {
            @Override
            public void onChanged(ChangedEvent event) {
            	selectItemVersions.clearValue();
                String selectedCategory = (String) event.getValue();
                if ("App 1".equals(selectedCategory)) {
                	selectItemVersions.setValueMap("1.1.0", "1.2.0", "1.2.5");
                } else if ("App 2".equals(selectedCategory)) {
                	selectItemVersions.setValueMap("2.1.0", "2.1.5", "2.2.0");
                } else if ("App 3".equals(selectedCategory)) {
                	selectItemVersions.setValueMap("3.1.0", "3.1.2", "3.2.0");
                } else if ("App 4".equals(selectedCategory)) {
                	selectItemVersions.setValueMap("4.1.0", "4.2.0", "4.2.1");
                }
            }
        });
        
        
        
        // Create TextItem Test Cycle
        TextItem textBoxCycle = new TextItem();
        textBoxCycle.setTitle("Test Cycle");
        
        // Create Label
        StaticTextItem staticTextItemMetrics = new StaticTextItem();
        staticTextItemMetrics.setShowTitle(false);
        staticTextItemMetrics.setValue("Metrics");
        
        // Create TextItem Metric 1
        TextItem textBoxMetric1 = new TextItem();
        textBoxMetric1.setTitle("Code Coverage (%)");

        // Create TextItem Metric 2
        TextItem textBoxMetric2 = new TextItem();
        textBoxMetric2.setTitle("Total Number of Defects");
        
        // Create TextItem Metric 3
        TextItem textBoxMetric3 = new TextItem();
        textBoxMetric3.setTitle("Average Response Time (ms)");
        
        // Create TextItem Metric 4
        TextItem textBoxMetric4 = new TextItem();
        textBoxMetric4.setTitle("Cyclomatic Complexity Index");
        textBoxMetric4.setDisabled(true);
        
        // Change Value to Metric 4
        textBoxMetric1.addChangedHandler(new ChangedHandler() {
        	@Override
        	public void onChanged(ChangedEvent event) {
        		float valueMetric1 = textBoxMetric1.getValueAsFloat();
        		int valueMetric1F = (int) (10 + (100 - valueMetric1) / 10);
        		textBoxMetric4.setValue(valueMetric1F);
        	}
        	
        });
        
        // Create TextItem Metric 5
        TextItem textBoxMetric5 = new TextItem();
        textBoxMetric5.setTitle("Defect Rate per Active User");
        textBoxMetric5.setDisabled(true);
        
        // Change Value to Metric 5
        textBoxMetric2.addChangedHandler(new ChangedHandler() {
        	@Override
        	public void onChanged(ChangedEvent event) {
        		int valueMetric2 = textBoxMetric2.getValueAsInteger();
        		float valueMetric3 = textBoxMetric3.getValueAsFloat();
        		int valueMetric2F = (int) (valueMetric2 / (valueMetric3 / 1000));
        		textBoxMetric5.setValue(valueMetric2F);
        	}
        	
        });
        
        textBoxMetric3.addChangedHandler(new ChangedHandler() {
        	@Override
        	public void onChanged(ChangedEvent event) {
        		int valueMetric2 = textBoxMetric2.getValueAsInteger();
        		float valueMetric3 = textBoxMetric3.getValueAsFloat();
        		int valueMetric2F = (int) (valueMetric2 / (valueMetric3 / 1000));
        		textBoxMetric5.setValue(valueMetric2F);
        	}
        	
        });

        
        
        // Styles Items
        textBoxCycle.setColSpan(2);
        staticTextItemMetrics.setColSpan(2);
        textBoxMetric1.setColSpan(4);
        textBoxMetric2.setColSpan(2);
        textBoxMetric3.setColSpan(2);
        textBoxMetric4.setColSpan(2);
        textBoxMetric5.setColSpan(2);
        
        // Create Button Save
        Button myButtonSave = new Button("Save");
        myButtonSave.setWidth(100);
        myButtonSave.setHeight(25);
        
        // Action Button Save
        myButtonSave.addClickHandler(event -> {
        	String app_nam = selectItemApps.getValueAsString();
            String app_version = selectItemVersions.getValueAsString();
            String cycle = textBoxCycle.getValueAsString();
            int metric1 = textBoxMetric1.getValueAsInteger();
            int metric2 = textBoxMetric2.getValueAsInteger();
            int metric3 = textBoxMetric3.getValueAsInteger();
            int metric4 = textBoxMetric4.getValueAsInteger();
            int metric5 = textBoxMetric5.getValueAsInteger();
            
            try {
            	DBService.insertMetrics(app_nam, app_version, cycle, metric1, metric2, metric3, metric4, metric5, new AsyncCallback<String>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        // Manejo de error
                        GWT.log("Error al guardar los datos", caught);
                    }

                    @Override
                    public void onSuccess(String result) {
                        // Acción a realizar cuando los datos se guardan correctamente
                        GWT.log("Datos guardados con exito");
                    }
                });
			} catch (Exception e) {
				e.printStackTrace();
			}
        });
        
        // Create Button Summary
        Button myButtonSummary = new Button("Summary");
        myButtonSummary.setWidth(100);
        myButtonSummary.setHeight(25);
        
        // Button Summary Action
        myButtonSummary.addClickHandler(event -> openModalWindow());
        
        // Create VLayout to Buttons
        VLayout buttonContainer = new VLayout();
        buttonContainer.setWidth(100);
        buttonContainer.setHeight(50);
        buttonContainer.setMembersMargin(10);
        buttonContainer.setAlign(Alignment.CENTER);
        
        // Add Buttons to VLayout
        buttonContainer.addMember(myButtonSave);
        buttonContainer.addMember(myButtonSummary);
        
        // Create HLayout to Encapsulate VLayout Buttons
        HLayout buttonsLayout = new HLayout();
        buttonsLayout.setWidth100();
        buttonsLayout.setAlign(Alignment.CENTER);
        buttonsLayout.setStyleName("buttonsLayout");
        
        // Add Encapsulate VLayout Buttons to HLayout Buttons
        buttonsLayout.addMember(buttonContainer);

        // Add SelectItem to DynamicForm SelectItems     
        form1.setFields(selectItemApps, 
        		selectItemVersions, 
        		textBoxCycle, 
        		staticTextItemMetrics ,
        		textBoxMetric1, 
        		textBoxMetric2, 
        		textBoxMetric3, 
        		textBoxMetric4, 
        		textBoxMetric5);

        // Add Elements to Main Container
        mainLayout.addMember(form1);
        mainLayout.addMember(buttonsLayout);
        
        // Draw Main Container
        mainLayout.draw();
	}
	
	// Method to Open Window Modal
    private void openModalWindow() {
    	
        // Create Window Modal
        Window modalWindow = new Window();
        modalWindow.setTitle("Summary");
        modalWindow.setWidth(600);
        modalWindow.setHeight(400);
        modalWindow.setIsModal(true);
        modalWindow.setShowModalMask(true);
        modalWindow.centerInPage();
        
        // Create VLayout to Elements
        VLayout elementsLayout = new VLayout();
        elementsLayout.setPadding(10);
        
        // Create DynamicForm to SelectItems
        DynamicForm formSelectItems = new DynamicForm();
        formSelectItems.setWidth100();
        formSelectItems.setNumCols(4);
        
        // Create DynamicForm to Metrics
        DynamicForm formSelectMetrics = new DynamicForm();
        formSelectMetrics.setWidth100();
        formSelectMetrics.setNumCols(4);

        // Create SelectItem Applications
        SelectItem selectItemAppsModal = new SelectItem();
        selectItemAppsModal.setTitle("Application");
        selectItemAppsModal.setType("text");
        selectItemAppsModal.setValueMap("App 1", "App 2", "App 3","App 4");
        
        // Create SelectItem Versions
        SelectItem selectItemVersionsModal = new SelectItem();
        selectItemVersionsModal.setTitle("Version");
        selectItemVersionsModal.setType("text");
        
        // Add ChangedHandler to First SelectItem Applications
        selectItemAppsModal.addChangedHandler(new ChangedHandler() {
            @Override
            public void onChanged(ChangedEvent event) {
            	selectItemVersionsModal.clearValue();
                String selectedCategory = (String) event.getValue();
                if ("App 1".equals(selectedCategory)) {
                	selectItemVersionsModal.setValueMap("1.1.0", "1.2.0", "1.2.5");
                } else if ("App 2".equals(selectedCategory)) {
                	selectItemVersionsModal.setValueMap("2.1.0", "2.1.5", "2.2.0");
                } else if ("App 3".equals(selectedCategory)) {
                	selectItemVersionsModal.setValueMap("3.1.0", "3.1.2", "3.2.0");
                } else if ("App 4".equals(selectedCategory)) {
                	selectItemVersionsModal.setValueMap("4.1.0", "4.2.0", "4.2.1");
                }
            }
        });
        
        // Add SelectItem Applications to DynamicForm
        formSelectItems.setFields(selectItemAppsModal, selectItemVersionsModal);
        
        // Create HLayout to Button
        HLayout buttonLayout = new HLayout();
        buttonLayout.setWidth100();
        buttonLayout.setHeight(25);
        buttonLayout.setAlign(Alignment.CENTER);
        buttonLayout.setStyleName("buttonsLayout");
        
        // Create Button Search
        Button myButtonSearch = new Button("Search");
        myButtonSearch.setWidth(100);
        myButtonSearch.setHeight(25); 
        
        // Add Button Search to HLayout
        buttonLayout.addMember(myButtonSearch);
        
        // Create Label
        StaticTextItem staticTextItemMetricsModal = new StaticTextItem();
        staticTextItemMetricsModal.setShowTitle(false);
        staticTextItemMetricsModal.setValue("Metrics");
        
        // Create TextItem Metric 1
        TextItem textBoxMetric1Modal = new TextItem();
        textBoxMetric1Modal.setTitle("Code Coverage (%)");
        textBoxMetric1Modal.setDisabled(true);
        
        // Create TextItem Metric 2
        TextItem textBoxMetric2Modal = new TextItem();
        textBoxMetric2Modal.setTitle("Total Number of Defects");
        textBoxMetric2Modal.setDisabled(true);
        
        // Create TextItem Metric 3
        TextItem textBoxMetric3Modal = new TextItem();
        textBoxMetric3Modal.setTitle("Total Number of Defects");
        textBoxMetric3Modal.setDisabled(true);
        
        // Create TextItem Metric 4
        TextItem textBoxMetric4Modal = new TextItem();
        textBoxMetric4Modal.setTitle("Cyclomatic Complexity Index");
        textBoxMetric4Modal.setDisabled(true);
        
        // Create TextItem Metric 5
        TextItem textBoxMetric5Modal = new TextItem();
        textBoxMetric5Modal.setTitle("Defect Rate per Active User");
        textBoxMetric5Modal.setDisabled(true);
        
        // Action Button Save
        myButtonSearch.addClickHandler(event -> {
        	String app_nam = (String) selectItemAppsModal.getValue();
            String app_version = selectItemVersionsModal.getValueAsString();
            
            try {
            	DBService.getMetrics(app_nam, app_version, new AsyncCallback<List<Metrics>>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        // Manejo de error
                        GWT.log("Error al consultar los datos", caught);
                    }

                    @Override
                    public void onSuccess(List<Metrics> result) {
                        // Acción a realizar cuando los datos se guardan correctamente
                        GWT.log("Datos consultados con exito");
                        if (result != null && !result.isEmpty()) {
                            Metrics metrics = result.get(0);
                            textBoxMetric1Modal.setValue(metrics.getMetric1());
                            textBoxMetric2Modal.setValue(metrics.getMetric2());
                            textBoxMetric3Modal.setValue(metrics.getMetric3());
                            textBoxMetric4Modal.setValue(metrics.getMetric4());
                            textBoxMetric5Modal.setValue(metrics.getMetric5());
                        } else {
                            // Manejo en caso de que la lista esté vacía
                            GWT.log("No se encontraron datos para las métricas");
                        }
                    }
                });
			} catch (Exception e) {
				e.printStackTrace();
			}
        });
        
        // Styles Items
        staticTextItemMetricsModal.setColSpan(2);
        textBoxMetric1Modal.setColSpan(2);
        textBoxMetric2Modal.setColSpan(2);
        textBoxMetric3Modal.setColSpan(2);
        textBoxMetric4Modal.setColSpan(2);
        textBoxMetric5Modal.setColSpan(2);
        
        // Add Metrics to DynamicForm
        formSelectMetrics.setFields(staticTextItemMetricsModal, 
        		textBoxMetric1Modal, 
        		textBoxMetric2Modal, 
        		textBoxMetric3Modal, 
        		textBoxMetric4Modal, 
        		textBoxMetric5Modal);
        
        // Add Elements to VLayout
        elementsLayout.addMember(formSelectItems);
        elementsLayout.addMember(buttonLayout);
        elementsLayout.addMember(formSelectMetrics);

        // Agregar el contenedor del formulario a la ventana modal
        modalWindow.addItem(elementsLayout);

        // Mostrar la ventana modal
        modalWindow.show();
    }
}
