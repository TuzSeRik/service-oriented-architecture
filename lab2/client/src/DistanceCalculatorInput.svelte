<script>
    let responseBody = 0
    $:  response = responseBody;
    let data = {}

    const sendData = async () => {
        let answer = await fetch("https://localhost:8181/service-one/distance-calculator?"
        // let answer = await fetch("https://localhost:8443/service-one-0.9.0/distance-calculator?"
            + new URLSearchParams(data).toString(),
            {
                method: 'GET'
            });
        responseBody = await answer.json();
    }
</script>

<main>
    <button on:click={() => sendData()}>Send Data</button>

    {response.toString()}
</main>

<style>
    main {
        display: flex;
        flex-direction: column;
    }
</style>
