# Cafe Sanchez Case Study
 This repository contains the second version of the Cafe Sanchez point-of-sale application. From the first version of the system, support for data persistence is added with the implementation of a data access layer that connects to a MS SQL Server database. This document describes the complete project, so it is not necessary to study the first version beforehand.

This project is written in Java and the IDE used is Eclipse. Language and IDE may change in later iterations.
 
 ## Introduction
 
 A point-of-sale (POS) system is a combination of hardware and software that allows businesses to process sales transactions, track inventory, and manage customer information. POS systems can include a variety of components, such as a cash register, barcode scanner, receipt printer, and credit card reader. Modern POS systems also often can connect to the internet, allowing for real-time inventory updates and data analysis. Additionally, many POS systems can integrate with other business software, such as accounting or customer relationship management systems. Overall, a POS system is a key tool for businesses to manage sales and inventory effectively.

### Scenario
Café Sanchez is a small coffee shop that is operated by the owner and one employee. This means taking the customers’ orders, processing them, and handing them out from the counter. To help in the day-to-day work, a system that can keep track of customer orders and support the workflow in the café, is desired.  

A typical workflow is described by the owner like this:  

*When a customer comes into the shop, she places an order for one or more beverages. The order is written onto a piece of paper with the customer’s name and is processed. When it is ready, the customer’s name is called and she steps up to the counter, pays, and get the beverages.*  
 
Through meetings with the café owner, the following additional information about the desired system is documented:  
* The system must be able to keep track of several orders simultaneously but does not keep records of the sales. 
* When an order is finished and paid for, it is deleted from the system. 
* Also, there is no need for handling payments.

As mentioned above, there are only two people working in the café and they are reasonably used to using IT systems. However, in certain times in a day there will be a lot of customers at the same time which causes a lot of hustle and bustle. In these situations it is important that the use of the system is as simple as possible, to prevent errors in the customers orders.

## Requirements
Based on the information provided above, the following *functional* requirements has been identified:  
1. **Order creation**: The software should allow the staff to create a new order, specifying the name of the customer, the beverages ordered, and any special instructions or preferences.
1. **Order processing**: Once an order is created, the software should allow the staff to process it, which may involve tracking its progress, and updating its status as it moves through the workflow.
1. **Order tracking**: The software should allow the staff to track the status of each order in real-time, so they can see which orders are in progress, which are ready to be picked up, and which are still waiting to be processed.
1. **Order deletion**: When an order is finished and paid for, the software should allow the staff to remove it from the system.
1. **Multiple order tracking**: The software should be able to keep track of several orders simultaneously, so the staff can work on multiple orders at the same time.

The only *non-functional* requirement identified is that the operation of the system must be fail-safe.

### Use Cases
The following brief use cases are identified:

**Create Order**: A customer arrives at the counter and places an order for one or more beverages. The cashier opens the systems *New Order* screen, asks for the customers name or moniker and enters it into the system and records which beverages are ordered. The system presents the total price for the order. The cashier presses the *Ok* button and the order is displayed on the main screen with status *New*.

**Process Order**: As soon as a new order is entered into the system, it can be processed. The barista selects the order, marks it as active, and starts producing the beverages. The order changes state to *Active* on the screen. When the barista is finished producing the beverages, they are placed on the counter for pickup by the customer. The barista marks the order as ready in the system and state changes to *Ready* on the screen. 

**Finish Order**: When the cashier sees an order has changed to the *Ready* state, he calls out the customer name/moniker that is connected to the order and the customer approaches the counter, settles the payment, and leaves the cafe with the ordered beverages. The cashier marks the order as *Finished* and it disappears from the screen.

## Implementation
The application is implemented as a desktop application based on a n-tier open architecture. The individual tiers are implemented as packages in the Java project.

![Architecture][architecture]

### UI
The user interface is designed to satisfy the Use Cases shown below and consists of two screens; a main window that shows the active orders that are currently being processed, and a modal window where details about a new customer order can be entered.

![Main Window][mainwindow]
![New Order Model][newordermodal]

### Business Logic
To support the use cases, a single controller for handling orders is implemented. This implements methods to fulfill the use cases and is also responsible for holding operation data and load master product data from a file. The master data is loaded once on startup, so the system does not support adding new products runtime, which is also not a requirement. In other words, if new products are added to the assortment, a restart is required. Also, since there is no requirement of keeping records of the sale, all data is stored in memory and will disappear when the system is shut down or restarted. The list for holding the order data is implemented in the OrderHandlingController class as a private property

![Business Layer][businesslayer]

### Model
The data needed for operating the system is represented by three model classes:

**Product Class**:  
The Product class represents a type of product that can be purchased in the system. Each product has a name and a price. 

**Order Class**:  
The Order class represents a customer order for one or more products. Each order has the name of an associated customer, and a timestamp of when the order was placed. It also contains information about the state the order currently have. An Order can have one or more OrderLines associated with it. The total price for the order is calculated by adding the subtotal from all orderlines.

**OrderLine Class**:  
The OrderLine class represents a single line item in an Order. It associates a product with a quantity that the customer has ordered. An OrderLine has a reference to the Product it represents and the quantity ordered. The subtotal price is calculated by multiplying the quantity by the price of the associated product.

The domain model allows the system to represent the relationship between these three classes. The OrderLine class connects the Product and Order classes and allows for the creation of multiple lines within an order. The Product class provides information about the products available, while the Order class tracks the customer's order and payment details.

![Domain Model][domainmodel]

### Data Access

**OrderDao Class**

**ProductDao Class**

![E/R-Diagram][erdiagram]

[architecture]: /Graphics/Architecture.svg "Architecture" 
[mainwindow]: /Graphics/screen1.png "Main screen with active orders"
[newordermodal]: /Graphics/screen2.png "Dialog for creating new orders"
[businesslayer]: /Graphics/business_layer.png "Business Logic Layer"
[domainmodel]: /Graphics/model_layer.png "Domain model"
[erdiagram]: /Graphics/ "Entity/Relation Diagram"