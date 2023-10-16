FROM node:14

WORKDIR /app

# Install dependencies. Not including package-lock.json because it's causing some issues due to the private registry
COPY package.json .
RUN npm install

# Copy the rest of the application files
COPY . .

EXPOSE 3000

ENTRYPOINT ["npm", "run", "dev", "--", "--host", "0.0.0.0"]
