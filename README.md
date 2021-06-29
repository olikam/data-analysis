## Problem Description

A customer, a multinational company that manufactures industrial appliances, has an internal system to procure (
purchase) all resources the company needs to operate. The procurement is done through the company's own ERP (Enterprise
Resource Planning) system.

A typical business process represented by the ERP system is _procure-to-pay_, which generally includes the following
activities:

* create purchase request
* request approved
* create purchase order
* select supplier
* receive goods
* pay invoice

Whenever the company wants to buy something, they do so through their ERP system.

The company buys many resources, always using their ERP system. Each resource purchase can be considered a case, or
single instance of this process. As it happens, the actual as-is process often deviates from the ideal to-be process.
Sometimes purchase requests are raised but never get approved, sometimes a supplier is selected but the goods are never
received, sometimes it simply takes a long time to complete the process, and so on. We call each unique sequence of
activities a variant.

The customer provides us with extracted process data from their existing ERP system. The customer extracted one of their
processes for analysis: Procure-to-pay. The logfiles contain three columns:

* activity name
* case id
* timestamp

We want to analyse and compare process instances (cases) with each other. You can find the sample data
set [here](src/main/resources/Activity_Log_2004_to_2014.csv).

## Acceptance Criteria

* Aggregate cases that have the same event execution order and list the 10 variants with the most cases.
* Provide your output as JSON, choose a structure that makes sense.
* As that output is used by other highly interactive components, we need to be able to get the query results in well
  under 50 milliseconds.

## Example

![Variants example](images/example.png)
