<script>
    export let response = {};
    let data = {}

    const sendData = async () => {
        let answer = await fetch("http://localhost:8080/lab1_server_war_exploded/distance-calculator?"
            + new URLSearchParams(data).toString(),
            {
                method: 'GET'
            });
        response = await answer.json();

        console.log(response);
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
