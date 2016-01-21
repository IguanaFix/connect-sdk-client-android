package com.globalcollect.gateway.sdk.client.android.exampleapp.render.field;

/**
 * Helper class for rendering a PaymentProductField
 * Copyright 2014 Global Collect Services B.V
 *
 */
public class RenderInputFieldHelper {

	
	// The parent view to which all fields are added
	private ViewGroup parentView;
	
	// Default renderer for tooltips on all fields
	private RenderTooltipInterface tooltipRenderer = new RenderTooltip();
	
	// Flag to determine if the first focus is set
	private Boolean isFocusSet = false;
	
	/**
	 * Constructor
	 * @param parentView, the ViewGroup to which all fields are added when rendered
	 */
	public RenderInputFieldHelper(ViewGroup parentView) {
		
		if (parentView == null) {
			throw new InvalidParameterException("Error initialising RenderField, parentView may not be null");
		}
		this.parentView = parentView;
	}
	
	
	/**
	 * Registers a custom tooltip renderer which is used for showing tooltips
	 * @param renderer, the renderer that is called when the tooltips are rendered.
	 */
	public void registerCustomTooltipRenderer(RenderTooltipInterface renderer) {
		
		if (renderer == null) {
			throw new InvalidParameterException("Error setting TooltipRenderer, renderer may not be null");
		}
		this.tooltipRenderer = renderer;
	}
	

	
	/**
	 * Renders a given field with the given renderer
	 * @param renderer, the RenderInputFieldInterface which determines how the PaymentProductField is rendered 
	 * @param field, the PaymentProductField which is rendered
	 * @param accountOnFile, the selected accountOnFile that contains previous payment data
	 * @param paymentRequest, the paymentRequest which contains all entered user input
	 */
	public void renderField(RenderInputFieldInterface renderer, PaymentProductField field, PaymentProduct selectedProduct, 
						    AccountOnFile accountOnFile, PaymentRequest paymentRequest, C2sPaymentProductContext context) {
	
		if (renderer != null) {
			
			// Create new row
			LinearLayout rowView = new LinearLayout(parentView.getContext());
			rowView.setOrientation(LinearLayout.VERTICAL);
			// Render field in row and set a tag so we can look it up later
			View view = renderer.renderField(field, selectedProduct, rowContentView, accountOnFile, paymentRequest, context);
			view.setTag(field.getId());
			
			// Set focus on the first field
			if (!isFocusSet) {
				view.requestFocus();
				isFocusSet = true;
			}
			
			// Render tooltip in row
			if (view.isEnabled()) {
				tooltipRenderer.renderTooltip(field.getId(), selectedProduct, rowContentView);
			}
		
			// Add row to parentView
			LayoutParams rowParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			parentView.addView(rowView, rowParams);
		}
	}
	
	
	public void resetFocus() {
		isFocusSet = false;
	}
}