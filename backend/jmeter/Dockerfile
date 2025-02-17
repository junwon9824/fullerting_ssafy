# Base image
FROM justb4/jmeter:latest

# Set environment variable for test plan file
ENV TEST_PLAN_FILE=/home/jmeter/your_test_plan.jmx

# Copy the JMeter test plan file into the container
COPY your_test_plan.jmx ${TEST_PLAN_FILE}

# Command to run JMeter
CMD ["jmeter", "-n", "-t", "/home/jmeter/your_test_plan.jmx", "-l", "/home/jmeter/results.jtl", "-e", "-o", "/home/jmeter/report"]
