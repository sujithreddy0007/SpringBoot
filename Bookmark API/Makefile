# ===========================
# BUILD TARGET
# ===========================
# Do not remove or rename this
.PHONY: java21-cli-build
java21-cli-build:
	mvn install -DtestFailureIgnore=true

# ===========================
# RUN TARGET
# ===========================
# Do not remove or rename this
.PHONY: java21-cli-run
java21-cli-run: ;

# ===========================
# RUN COMMAND (IDE RUN BUTTON)
# ===========================
# Starts the Spring Boot application safely
.PHONY: lab-run
lab-run:
	# ensure tmp directory exists
	mkdir -p ./tmp
	# compile code
	mvn compile
	# stop any previous Spring Boot instance
	mvn spring-boot:stop || true
	# start Spring Boot app
	mvn spring-boot:start -Dspring-boot.run.jvmArguments="-Djava.io.tmpdir=./tmp"

# ===========================
# TEST COMMAND (IDE TEST BUTTON)
# ===========================
# Runs the currently open test file only
.PHONY: lab-test
lab-test:
ifeq ($(FILE),)
	@echo "No file open, running all tests..."
	mvn test
else
	@echo "Running test for file: $(FILE)"
	mvn -Dtest=$(subst src/test/java/,,$(basename $(FILE))) test
endif
